package weather.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="PROVIDERS", schema = "TOP_WEATHER")
public class Provider extends BaseEntity implements Serializable {

    @Id
    @Column(name="PROVIDER_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long providerId;

    @Column(name="NAME")
    private String name;

    @Column(name="UI_LINK")
    private String uiLink;

    @Column(name="ADD_DATE")
    private Date addDate;

    @Column(name="UPDATE_DATE")
    private Date updateDate;

    @OneToMany(mappedBy = "provider", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RequestRule> requestRules = new ArrayList<RequestRule>();

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUiLink() {
        return uiLink;
    }

    public void setUiLink(String uiLink) {
        this.uiLink = uiLink;
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

    public List<RequestRule> getRequestRules() {
        return requestRules;
    }

    public void setRequestRules(List<RequestRule> requestRules) {
        this.requestRules = requestRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        return (providerId != null ? providerId.equals(provider.providerId) : (name != null ? name.equals(provider.name) : false));
    }

    @Override
    public int hashCode() {
        int result = providerId != null ? providerId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}