package weather.dao;

import weather.model.Request;
import weather.model.enumeration.Period;

import java.util.List;

public interface RequestDao extends Dao<Request> {
    List<Request> getForecastForPeriod(String locationUID, Long providerId, Period period);
}
