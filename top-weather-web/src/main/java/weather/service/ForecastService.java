package weather.service;

import weather.dto.ForecastRateDto;
import weather.dto.RateTableDto;
import weather.model.Forecast;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.util.Pair;

import java.util.List;

public interface ForecastService extends Service<Forecast> {
    List<ForecastRateDto> getForecastByRate(String locationUID, FeatureType featureType, Period period, OrderType orderType);

    RateTableDto getForecast(String locationUID, Pair<Period, FeatureType> orderField, OrderType orderType);
}
