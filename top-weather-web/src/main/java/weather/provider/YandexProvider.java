package weather.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import weather.model.Forecast;
import weather.model.Request;
import weather.model.RequestRule;
import weather.model.enumeration.FeatureType;

import java.io.IOException;
import java.util.*;

/**
 * @author vosipenko
 *         on 15.02.2015.
 */
//@Component("pogoda.yandex.by")
public class YandexProvider implements WeatherForecastProvider {

    @Override
    public List<Request> getWeather(RequestRule requestRule) {
        List<Request> requestList = new ArrayList<Request>();
        Connection connection = Jsoup.connect(requestRule.getRequestLink());
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            Date currentDate = new Date();
            calendar.setTime(currentDate);

            Document doc = connection.get();
            for (Element element : doc.getElementsByClass("weather-table__body")) {
                Request request = new Request();
                requestList.add(request);
                request.setRequestDate(currentDate);
                request.setForecastDate(calendar.getTime());
                request.setRequestRule(requestRule);

                System.out.println(calendar.getTime() + "*----------------------------------------");
                for (Element tr : element.getElementsByTag("tr")) {
                    String temperatureStr = tr.getElementsByClass("weather-table__temp").text().trim().replaceAll("−", "-");
                    StringTokenizer stringTokenizer = new StringTokenizer(temperatureStr, "…");
                    int temperatureFrom = Integer.parseInt(stringTokenizer.nextElement().toString());
                    int temperatureTo = temperatureFrom;
                    if (stringTokenizer.hasMoreElements())
                        temperatureTo = Integer.parseInt(stringTokenizer.nextElement().toString());

                    int temperature = ((temperatureFrom + temperatureTo) / 2);
                    Forecast forecast = new Forecast(FeatureType.TEMPERATURE_DAY);
                    forecast.setRequest(request);
                    forecast.setValue(String.valueOf(temperature));

                    request.getForecasts().put(FeatureType.TEMPERATURE_DAY, forecast);

                    System.out.println(tr.getElementsByClass("weather-table__body-cell_type_condition").text().trim());
                }
                calendar.add(Calendar.DATE, 1);

            }


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return requestList;
    }

}
