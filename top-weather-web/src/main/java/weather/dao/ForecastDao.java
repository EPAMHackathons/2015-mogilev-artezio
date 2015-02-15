package weather.dao;

import weather.dto.ForecastRateDto;
import weather.model.Forecast;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;

import java.util.List;

public interface ForecastDao extends Dao<Forecast> {
    List<ForecastRateDto> getForecastByRate(String locationUID, FeatureType featureType, Period period, OrderType orderType);
}
