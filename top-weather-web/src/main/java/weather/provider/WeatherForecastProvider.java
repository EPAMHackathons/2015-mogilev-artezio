package weather.provider;


import weather.model.Request;
import weather.model.RequestRule;

import java.util.List;

public interface WeatherForecastProvider {
 List<Request> getWeather(RequestRule  requestRule);
}
