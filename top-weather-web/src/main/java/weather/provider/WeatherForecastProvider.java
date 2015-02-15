package weather.provider;


import weather.model.Request;

import java.util.List;

public interface WeatherForecastProvider {
 List<Request> getWeather(String providerUrl);
}
