package weather.dto;


import weather.model.Provider;
import weather.model.enumeration.FeatureType;
import weather.model.enumeration.Period;

public class ForecastRateDto {

    private Provider provider;

    private Double rate;

    private FeatureType featureType;

    private Period period;

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
