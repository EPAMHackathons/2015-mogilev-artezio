package weather.service;

import org.springframework.stereotype.Component;
import weather.dao.RequestDao;
import weather.model.Request;
import weather.model.enumeration.Period;

import java.util.List;

@Component
public class RequestServiceImpl extends AbstractSpringService<RequestDao, Request> implements RequestService {

    public List<Request> getForecastForPeriod(String locationUID, Long providerId, Period period) {
        return getDao().getForecastForPeriod(locationUID, providerId, period);
    }

}
