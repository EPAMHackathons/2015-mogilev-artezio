package weather.beans;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import weather.dto.RateTableDto;
import weather.dto.RateTableRowDto;
import weather.dto.RequestDto;
import weather.model.Provider;
import weather.model.Forecast;
import weather.model.Request;
import weather.model.RequestRule;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.OrderType;
import weather.model.enumeration.Period;
import weather.service.*;
import weather.util.Pair;
import weather.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        RateTableDto tableDto = forecastService.getForecast(locationUID, orderField, orderType);
        for (Map.Entry<Provider, RateTableRowDto> entry: tableDto.getRowDtoMap().entrySet()) {
            entry.getValue().setRequests(getForecastForPeriod(locationUID, entry.getKey().getProviderId(), Period.WEEK));
        }
        return tableDto;
    }

    public void calculateRate() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();

        List<Request> proveRequests = requestService.getRequestsByDate(today, today, null);

        for (Request request : proveRequests) {
            RequestRule requestRule = request.getRequestRule();
            if (request.getForecasts().containsKey(FeatureType.TEMPERATURE_DAY)) {
                Forecast proveForecast = request.getForecasts().get(FeatureType.TEMPERATURE_DAY);
                if (StringUtils.isNotEmpty(proveForecast.getValue())) {
                    List<Request> rateCalculatedRequests = requestService.getRequestsByDate(yesterday, today, requestRule.getRequestRuleId());

                    if (rateCalculatedRequests.size() != 1) {
                        System.out.println("No single request by rule for day");
                    }

                    for (Request ratedRequest : rateCalculatedRequests) {
                        if(ratedRequest.getForecasts().containsKey(FeatureType.TEMPERATURE_DAY)) {
                            Forecast ratedForecast = ratedRequest.getForecasts().get(FeatureType.TEMPERATURE_DAY);
                            Integer rate = Utils.calcTempRate(ratedForecast.getValue(), proveForecast.getValue());
                            if (rate != null) {
                                System.out.println(String.format("Rate calculated. rate=%d", rate));
                                ratedForecast.setRate(rate);
                                forecastService.save(ratedForecast);
                            } else {
                                System.out.println(String.format("Rate not calculated. ratedForecast=%s, proveForecast=%s", ratedForecast.getValue(), proveForecast.getValue()));
                            }
                        }
                    }

                } else {
                    System.out.println("Rated forecast value empty");
                }
            }
        }
    }

}
