package weather.model;

import weather.model.enumeration.FeatureType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="FORECASTS", schema = "TOP_WEATHER")
public class Forecast extends BaseEntity implements Serializable {

    @Id
    @Column(name="FORECAST_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long forecastId;

    @Column(name="UPDATE_DATE")
    private Date updateDate;

    @Column(name = "PARAM")
    @Enumerated(EnumType.STRING)
    private FeatureType featureType;

    @Column(name="VALUE")
    private String value;

    @Column(name="RATE")
    private Integer rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REQUEST_ID", nullable = false)
    private Request request;

    public Forecast() {
    }

    public Forecast(FeatureType featureType) {
        this.featureType = featureType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VERIFICATION_FORECAST_ID", nullable = true)

    private Forecast verificationForecast;

    public Long getForecastId() {
        return forecastId;
    }

    public void setForecastId(Long forecastId) {
        this.forecastId = forecastId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Forecast getVerificationForecast() {
        return verificationForecast;
    }

    public void setVerificationForecast(Forecast verificationForecast) {
        this.verificationForecast = verificationForecast;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}