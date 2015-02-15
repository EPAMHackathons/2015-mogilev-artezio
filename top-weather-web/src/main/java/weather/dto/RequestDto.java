package weather.dto;

import weather.model.Forecast;
import weather.model.RequestRule;
import weather.model.enumeration.FeatureType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RequestDto {

    private Date requestDate;

    private String requestDayOfWeek;

    private Date forecastDate;

    private String forecastDayOfWeek;

    private RequestRule requestRule;

    private Map<FeatureType, Forecast> forecasts = new HashMap<FeatureType, Forecast>();

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestDayOfWeek() {
        return requestDayOfWeek;
    }

    public void setRequestDayOfWeek(String requestDayOfWeek) {
        this.requestDayOfWeek = requestDayOfWeek;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public String getForecastDayOfWeek() {
        return forecastDayOfWeek;
    }

    public void setForecastDayOfWeek(String forecastDayOfWeek) {
        this.forecastDayOfWeek = forecastDayOfWeek;
    }

    public RequestRule getRequestRule() {
        return requestRule;
    }

    public void setRequestRule(RequestRule requestRule) {
        this.requestRule = requestRule;
    }

    public Map<FeatureType, Forecast> getForecasts() {
        return forecasts;
    }

    public void setForecasts(Map<FeatureType, Forecast> forecasts) {
        this.forecasts = forecasts;
    }
}
