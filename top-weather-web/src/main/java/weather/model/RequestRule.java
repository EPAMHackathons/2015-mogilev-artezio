package weather.model;

import weather.model.enumeration.RequestTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="REQUEST_RULES", schema = "TOP_WEATHER")
public class RequestRule extends BaseEntity implements Serializable {

    @Id
    @Column(name="REQUEST_RULE_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long requestRuleId;

    @Column(name = "REQUEST_TIME")
    @Enumerated(EnumType.STRING)
    private RequestTime requestTime;

    @Column(name="NAME")
    private String name;

    @Column(name="REQUEST_LINK")
    private String requestLink;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROVIDER_ID", nullable = false)
    private Provider provider;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCATION_ID", nullable = false)
    private Location location;

    @Column(name="ADD_DATE")
    private Date addDate;

    @Column(name="UPDATE_DATE")
    private Date updateDate;

    @OneToMany(mappedBy = "requestRule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Request> requests = new ArrayList<Request>();

    public Long getRequestRuleId() {
        return requestRuleId;
    }

    public void setRequestRuleId(Long requestRuleId) {
        this.requestRuleId = requestRuleId;
    }

    public RequestTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(RequestTime requestTime) {
        this.requestTime = requestTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestLink() {
        return requestLink;
    }

    public void setRequestLink(String requestLink) {
        this.requestLink = requestLink;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}