package weather.model;

import weather.model.enumeration.FeatureType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="REQUESTS", schema = "TOP_WEATHER")
public class Request extends BaseEntity implements Serializable {

    @Id
    @Column(name="REQUEST_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long requestId;

    @Column(name="REQUEST_DATE")
    private Date requestDate;

    @Column(name="FORECAST_DATE")
    private Date forecastDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUEST_RULE_ID", nullable = false)
    private RequestRule requestRule;

    @OneToMany(mappedBy="request", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @MapKey(name = "featureType")
    private Map<FeatureType, Forecast> forecasts = new HashMap<FeatureType, Forecast>();

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
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