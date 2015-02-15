package weather.service;

import weather.model.Request;
import weather.model.enumeration.Period;

import java.util.Date;
import java.util.List;

public interface RequestService extends Service<Request> {

    List<Request> getForecastForPeriod(String locationUID, Long providerId, Period period);
    List<Request> getRequestsByDate(Date requestDate, Date forecastDate, Long ruleId);
}
