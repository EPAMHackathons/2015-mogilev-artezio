package weather.provider;


import weather.model.Forecast;

import java.util.List;

public interface WeatherForecastProvider {
 List<Forecast> getWeather(String providerUrl);
}
