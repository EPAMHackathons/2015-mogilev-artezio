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
import weather.model.weather.enumeration.*;

import java.util.*;
@Component("pogoda.by")
public class PogodaBYProvider implements WeatherForecastProvider {

    @Override
    public List<Request> getWeather(RequestRule rule) {
        List<Request> weeksRequests;
        try {
            long currentTime=new Date().getTime();
            Connection connection = Jsoup.connect(rule.getRequestLink());
            Document doc = connection.get();

            Element forecastWeather = doc.getElementById("forecast");
            Elements forecastRows = forecastWeather.getElementsByTag("tr");

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentTime);
            int rowNumber = 1;
            int dayCount = 1;
            weeksRequests = new ArrayList<Request>();
            Date weatherDay = null;
            Request dayRequest = null;
            for (; rowNumber < forecastRows.size(); rowNumber++) {
                Elements forecastCells = forecastRows.get(rowNumber).getElementsByTag("td");
                String periodOfDayString = forecastCells.get(0).text().trim();
                PeriodOfDay periodOfDay = null;
                if (periodOfDayString != null) {
                    if (periodOfDayString.equals("день"))
                        periodOfDay = PeriodOfDay.DAY;
                    else if (periodOfDayString.equals("утро"))
                        periodOfDay = PeriodOfDay.MORNING;
                    else if (periodOfDayString.equals("вечер"))
                        periodOfDay = PeriodOfDay.EVENING;
                    else if (periodOfDayString.equals("ночь"))
                        periodOfDay = PeriodOfDay.NIGHT;
                }

                if (periodOfDay != null) {
                    if (periodOfDay.equals(PeriodOfDay.DAY) || periodOfDay.equals(PeriodOfDay.NIGHT)) {
                            weeksRequests.add(parseForecastWeather(weatherDay, periodOfDay, forecastCells, rule, dayRequest));
                    }
                } else {
                    if(dayCount == 6)
                    {
                        break;
                    }
                    dayRequest = new Request();
                    if(weatherDay != null) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    dayCount++;
                    weatherDay = calendar.getTime();
                }
            }
        } catch (Exception e) {
            weeksRequests = null;
        }
        return weeksRequests;
    }

    private Request parseForecastWeather(Date weatherDay, PeriodOfDay periodOfDay, Elements forecastCells, RequestRule rule, Request dayRequest) {

        String temperatureStr = forecastCells.get(1).text();
        int temperatureFrom = 0;
        int temperatureTo = 0;

        if (temperatureStr.toLowerCase().contains("около")) {
            temperatureFrom = Integer.parseInt(temperatureStr.substring(5).trim());
            temperatureTo = temperatureFrom;
        } else {
            temperatureFrom = Integer.parseInt(temperatureStr.substring(0, temperatureStr.indexOf(".")).replace("+", ""));
            temperatureTo = Integer.parseInt(temperatureStr.substring(temperatureStr.lastIndexOf(".") + 1).replace("+", ""));
        }
        int temperature = (int) ((temperatureFrom + temperatureTo) / 2);
        String overcastString = forecastCells.get(3).text();
        String overcast = overcastDecode(overcastString.substring(0, overcastString.indexOf(".")));
        String phenomens = phenomensDecode(overcastString.substring(overcastString.indexOf(".") + 1).trim().toLowerCase());


        Forecast temperatureForecast = new Forecast();
        temperatureForecast.setRequest(dayRequest);
        FeatureType featuretype;
        if(periodOfDay.equals(PeriodOfDay.DAY)){
            featuretype = FeatureType.TEMPERATURE_DAY;
        } else{
            featuretype = FeatureType.TEMPERATURE_NIGHT;
        }
        temperatureForecast.setFeatureType(featuretype);
        temperatureForecast.setValue(Integer.toString(temperature));
        dayRequest.getForecasts().put(featuretype, temperatureForecast);

        if(periodOfDay.equals(PeriodOfDay.DAY)){
            featuretype = FeatureType.OVERCAST_DAY;
        } else{
            featuretype = FeatureType.OVERCAST_NIGHT;
        }
        Forecast overcastForecast = new Forecast();
        overcastForecast.setRequest(dayRequest);
        overcastForecast.setFeatureType(featuretype);
        overcastForecast.setValue(overcast);
        dayRequest.getForecasts().put(featuretype, overcastForecast);

        if(periodOfDay.equals(PeriodOfDay.DAY)){
            featuretype = FeatureType.PHENOMENA_DAY;
        } else{
            featuretype = FeatureType.PHENOMENA_NIGHT;
        }
        Forecast phenomenaForecast = new Forecast();
        phenomenaForecast.setRequest(dayRequest);
        phenomenaForecast.setFeatureType(featuretype);
        phenomenaForecast.setValue(phenomens);
        dayRequest.getForecasts().put(featuretype, phenomenaForecast);
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
        if (overcast.equals("Сплошная облачность")) {
            return Overcast.OVC.name();
        } else if (overcast.equals("Облачно с прояснениями")) {
            return Overcast.BKN.name();
        } else if (overcast.equals("Переменная облачность")) {
            return Overcast.SCT.name();
        } else if (overcast.equals("Небольшая облачность")) {
            return Overcast.FEW.name();
        } else if (overcast.equals("Малооблачно")) {
            return Overcast.NSC.name();
        } else if (overcast.equals("Ясно")) {
            return Overcast.SKC.name();
        } else {
            return Overcast.NONE.name();
        }
    }
}
