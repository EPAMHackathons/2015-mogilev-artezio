package weather.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import weather.model.Forecast;
import weather.model.Request;
import weather.model.RequestRule;
import weather.model.enumeration.FeatureType;
import weather.model.weather.enumeration.Overcast;
import weather.model.weather.enumeration.PeriodOfDay;
import weather.model.weather.enumeration.Phenomen;

import java.io.IOException;
import java.util.*;

/**
 * @author vosipenko
 *         on 15.02.2015.
 */
@Component("pogoda.yandex.by")
public class YandexProvider implements WeatherForecastProvider {

    @Override
    public List<Request> getWeather(RequestRule requestRule) {
        List<Request> weeksRequests;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        int dayCount = 1;
        try {
            Connection connection = Jsoup.connect(requestRule.getRequestLink());
            Document doc = connection.get();
            weeksRequests = new ArrayList<Request>();
            Request dayRequest = new Request();
            for (Element forecastWeather : doc.getElementsByClass("weather-table__body")) {
                Elements forecastRows = forecastWeather.getElementsByTag("tr");
                int rowNumber = 0;
                for (; rowNumber < forecastRows.size(); rowNumber++) {
                    Elements forecastCells = forecastRows.get(rowNumber).getElementsByTag("td");
                    String periodOfDayString = forecastCells.get(0).child(0).text().toLowerCase();
                    PeriodOfDay periodOfDay = null;
                    if (periodOfDayString != null) {
                        if (periodOfDayString.equals("днем"))
                            periodOfDay = PeriodOfDay.DAY;
                        else if (periodOfDayString.equals("утром"))
                            periodOfDay = PeriodOfDay.MORNING;
                        else if (periodOfDayString.equals("вечером"))
                            periodOfDay = PeriodOfDay.EVENING;
                        else if (periodOfDayString.equals("ночью"))
                            periodOfDay = PeriodOfDay.NIGHT;
                    }
                    if (periodOfDay != null && (periodOfDay.equals(PeriodOfDay.DAY) || periodOfDay.equals(PeriodOfDay.NIGHT))) {
                        weeksRequests.add(parseForecastWeather(calendar.getTime(), periodOfDay, forecastCells, requestRule, dayRequest));
                    }
                }
                if(dayCount == 5)
                {
                    break;
                }
                dayCount++;
                dayRequest = new Request();
                calendar.add(Calendar.DATE, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return weeksRequests;
    }
    private Request parseForecastWeather(Date weatherDay, PeriodOfDay periodOfDay, Elements forecastCells, RequestRule rule, Request dayRequest) {

        String temperatureStr = forecastCells.get(0).child(1).text();
        int temperatureFrom = 0;
        int temperatureTo = 0;

        if (!temperatureStr.contains("…")) {
            temperatureFrom = Integer.parseInt(temperatureStr.replace("+", "").replace("−", "-").trim());
            temperatureTo = temperatureFrom;
        } else {
            temperatureFrom = Integer.parseInt(temperatureStr.substring(0, temperatureStr.indexOf("…")).replace("+", "").replace("−", "-"));
            temperatureTo = Integer.parseInt(temperatureStr.substring(temperatureStr.lastIndexOf("…") + 1).replace("+", "").replace("−", "-"));
        }
        int temperature = (int) ((temperatureFrom + temperatureTo) / 2);
        String overcastPhenomenasString = forecastCells.get(2).text();
        String overcast = null;
        String phenomens = null;
        if(overcastPhenomenasString.contains(",")){
            overcast = overcastDecode(overcastPhenomenasString.substring(0, overcastPhenomenasString.indexOf(",")));
            phenomens = phenomensDecode(overcastPhenomenasString.substring(overcastPhenomenasString.indexOf(",") + 1).trim().toLowerCase());
        } else {
            overcast = overcastDecode(overcastPhenomenasString.trim());
        }

        Forecast temperatureForecast = new Forecast();
        FeatureType featuretype;
        if(periodOfDay.equals(PeriodOfDay.DAY)){
            featuretype = FeatureType.TEMPERATURE_DAY;
        } else{
            featuretype = FeatureType.TEMPERATURE_NIGHT;
        }
        temperatureForecast.setFeatureType(featuretype);
        temperatureForecast.setValue(Integer.toString(temperature));
        dayRequest.getForecasts().put(featuretype, temperatureForecast);
        temperatureForecast.setRequest(dayRequest);

        if(periodOfDay.equals(PeriodOfDay.DAY)){
            featuretype = FeatureType.OVERCAST_DAY;
        } else{
            featuretype = FeatureType.OVERCAST_NIGHT;
        }
        Forecast overcastForecast = new Forecast();
        overcastForecast.setFeatureType(featuretype);
        overcastForecast.setValue(overcast);
        dayRequest.getForecasts().put(featuretype, overcastForecast);
        overcastForecast.setRequest(dayRequest);

        if(periodOfDay.equals(PeriodOfDay.DAY)){
            featuretype = FeatureType.PHENOMENA_DAY;
        } else{
            featuretype = FeatureType.PHENOMENA_NIGHT;
        }
        Forecast phenomenaForecast = new Forecast();
        phenomenaForecast.setFeatureType(featuretype);
        phenomenaForecast.setValue(phenomens);
        dayRequest.getForecasts().put(featuretype, phenomenaForecast);
        phenomenaForecast.setRequest(dayRequest);
        dayRequest.setForecastDate(weatherDay);
        dayRequest.setRequestDate(new Date());
        dayRequest.setRequestRule(rule);
        return dayRequest;

    }

    private String phenomensDecode(String phenomens) {
        String phenomensStr = null;

        if (phenomens != null && phenomens.trim().length() > 0) {
            String[] phenomensArray = null;
            if (phenomens.contains("."))
                phenomensArray = phenomens.split("\\.");
            else
                phenomensArray = phenomens.split(",");

            if (phenomensArray.length != 0) {
                StringBuilder phenomensBuilder = new StringBuilder();
                for (int i = 0; i < phenomensArray.length; i++) {
                    phenomensBuilder.append(decodePhenomen(phenomensArray[i]));
                    if(i < (phenomensArray.length - 1)){
                        phenomensBuilder.append(",");
                    }
                }
                phenomensStr = phenomensBuilder.toString();
            }
        }
        return phenomensStr;
    }

    private String decodePhenomen(String phenomenStr) {
        String phenomen = null;
        if (phenomenStr.contains("туман"))
            phenomen = Phenomen.FG.name();
        else if (phenomenStr.contains("дымка"))
            phenomen = Phenomen.BR.name();
        else if (phenomenStr.contains("ливневый снег"))
            phenomen = Phenomen.SHSN.name();
        else if (phenomenStr.contains("мокрый снег"))
            phenomen = Phenomen.SNRA.name();
        else if (phenomenStr.contains("снег"))
            phenomen = Phenomen.SN.name();
        else if (phenomenStr.contains("дождь"))
            phenomen = Phenomen.RA.name();
        else if (phenomenStr.contains("метель"))
            phenomen = Phenomen.BLSN.name();
        return phenomen;
    }

    private String overcastDecode(String overcast) {
        if (overcast.equals("облачно")) {
            return Overcast.OVC.name();
        } else if (overcast.equals("облачно с прояснениями")) {
            return Overcast.BKN.name();
        } else if (overcast.equals("переменная облачность")) {
            return Overcast.SCT.name();
        } else if (overcast.equals("небольшая облачность")) {
            return Overcast.FEW.name();
        } else if (overcast.equals("малооблачно")) {
            return Overcast.NSC.name();
        } else if (overcast.equals("ясно")) {
            return Overcast.SKC.name();
        } else {
            return Overcast.NONE.name();
        }
    }
}
