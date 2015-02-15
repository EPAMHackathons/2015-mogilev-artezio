package weather.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="LOCATIONS", schema = "TOP_WEATHER")
public class Location extends BaseEntity implements Serializable {

    @Id
    @Column(name="LOCATION_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long locationId;

    @Column(name="NAME")
    private String name;

    @Column(name="UID")
    private String uid;

    @Column(name="COUNTRY")
    private String country;

    @Column(name="CITY")
    private String city;

    @Column(name="GPS_POSITION")
    private String gpsPosition;

    @Column(name="ADD_DATE")
    private Date updateDate;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestRule> requestRules = new ArrayList<RequestRule>();

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGpsPosition() {
        return gpsPosition;
    }

    public void setGpsPosition(String gpsPosition) {
        this.gpsPosition = gpsPosition;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<RequestRule> getRequestRules() {
        return requestRules;
    }

    public void setRequestRules(List<RequestRule> requestRules) {
        this.requestRules = requestRules;
    }
}