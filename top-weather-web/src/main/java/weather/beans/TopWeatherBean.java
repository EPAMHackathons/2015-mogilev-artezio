package weather.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weather.dto.RateTableDto;
import weather.dto.RequestDto;
import weather.model.Request;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.service.*;
import weather.util.Pair;
import weather.util.Utils;

import java.util.List;

@Component
public class TopWeatherBean {

    @Autowired
    private RequestService requestService;

    @Autowired
    private ForecastService forecastService;


    /**
     * Request forecast for period(day/week...)
     * @param locationUID
     * @param providerId
     * @param period
     * @return forecast for period
     */
    public List<RequestDto> getForecastForPeriod(String locationUID, Long providerId, Period period) {
        List<Request> requests = requestService.getForecastForPeriod(locationUID, providerId, period);
        List<RequestDto> requestsDto = Utils.requestConverter(requests);
        return requestsDto;
    }

    /**
     *  Main rate table
     * @param locationUID
     * @param orderField
     * @param orderType
     * @return rate table
     */
    public RateTableDto getForecast(String locationUID, Pair<Period, FeatureType> orderField, OrderType orderType) {
        return forecastService.getForecast(locationUID, orderField, orderType);
    }

}
