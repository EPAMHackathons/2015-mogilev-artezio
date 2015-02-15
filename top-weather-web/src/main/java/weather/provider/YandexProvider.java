package weather.provider;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weather.model.Request;
import weather.model.RequestRule;
import weather.service.RequestService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author vosipenko
 *         on 15.02.2015.
 */
@Component
public class YandexProvider implements WeatherForecastProvider {
    @Autowired(required = false)
    private RequestService requestService;

    @Override
    public List<Request> getWeather(RequestRule requestRule) {
        Connection connection = Jsoup.connect(requestRule.getRequestLink());
        try {
            Calendar calendar= GregorianCalendar.getInstance();
            calendar.setTime(new Date());

            Document doc = connection.get();
            for (Element element : doc.getElementsByClass("weather-table")) {

                System.out.println(calendar.getTime()+"*----------------------------------------");
                for (Element tr : element.getElementsByTag("tr")) {
                    for (Element td : tr.getElementsByTag("td")) {
                        System.out.print(td.getElementsByClass("weather-table__temp").text().trim());
                        System.out.println(td.getElementsByClass("weather-table__body-cell_type_condition").text().trim());
                    }


                }
            calendar.add(Calendar.DATE, 1);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
