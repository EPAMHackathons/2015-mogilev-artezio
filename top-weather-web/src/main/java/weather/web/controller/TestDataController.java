package weather.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import weather.beans.TopWeatherBean;
import weather.model.Forecast;
import weather.model.Location;
import weather.model.Request;
import weather.model.RequestRule;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.RequestTime;
import weather.service.LocationService;
import weather.service.RequestService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class TestDataController {

    Random randomRate = new Random();
    Random randomParamValue = new Random();

    @Autowired
    private TopWeatherBean topWeatherBean;

    @Autowired
    private RequestService requestService;

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/rate.html")
    @Transactional
    public void calcRate(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        topWeatherBean.calculateRate();

        response.setContentType("text/html");
        response.resetBuffer();
        response.getWriter().print("Hello");
    }


    @RequestMapping(value = "/test.html")
    @Transactional
    public void generateTestData(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");

        List<Location> locations = locationService.getAll();
        List<Date> dates = new ArrayList<Date>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        for (int i = 0; i < 3 ; i++) {
            calendar.add(Calendar.DATE, 1);
            dates.add(calendar.getTime());
        }

        int readyPercent = 0;
        System.out.println("Test data generation");
        for (Date requestDate : dates) {
            for (Location location : locations) {
                for (RequestRule requestRule : location.getRequestRules()) {
                    if (RequestTime.WEEK.equals(requestRule.getRequestTime())) {
                        Calendar calendarTomorrow = Calendar.getInstance();
                        calendarTomorrow.setTime(requestDate);
                        for (int i = 1 ; i <= 7; i++) {
                            requestService.save(createRequest(requestRule, requestDate, calendarTomorrow.getTime()));
                            calendarTomorrow.add(Calendar.DATE, 1);
                        }
                    }
                }
            }
            readyPercent += 100 / dates.size();
            System.out.println(String.format("Test data generated for %d percent", readyPercent));
        }

        System.out.println(String.format("Test data generated for %d percent", 100));

        response.setContentType("text/html");
        response.resetBuffer();
        response.getWriter().print("Hello");
    }

    private Request createRequest(RequestRule requestRule, Date requestDate, Date forecastDate) {
        Request request = new Request();
        request.setRequestRule(requestRule);
        request.setRequestDate(requestDate);
        request.setForecastDate(forecastDate);
        for (FeatureType featureType : FeatureType.values()) {
            Forecast forecast = new Forecast();
            forecast.setFeatureType(featureType);
            forecast.setRate(randomRate.nextInt(101));
            forecast.setValue(String.valueOf(randomParamValue.nextInt(50) - 25));
            forecast.setRequest(request);
            request.getForecasts().put(featureType, forecast);

        }
        return request;
    }

}
