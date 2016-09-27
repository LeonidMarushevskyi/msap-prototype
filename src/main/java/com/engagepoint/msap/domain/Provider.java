package com.engagepoint.msap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Provider.
 */
@Entity
@Table(name = "provider")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "provider")
public class Provider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "provider_capacity")
    private Integer providerCapacity;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToOne
    private LookupLicenseType licenseType;

    @OneToOne
    private LookupProviderType providerType;

    @OneToOne
    private Place address;

    @OneToOne
    private LookupQualityRating qualityRating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProviderCapacity() {
        return providerCapacity;
    }

    public void setProviderCapacity(Integer providerCapacity) {
        this.providerCapacity = providerCapacity;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LookupLicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LookupLicenseType lookupLicenseType) {
        this.licenseType = lookupLicenseType;
    }

    public LookupProviderType getProviderType() {
        return providerType;
    }

    public void setProviderType(LookupProviderType lookupProviderType) {
        this.providerType = lookupProviderType;
    }

    public Place getAddress() {
        return address;
    }

    public void setAddress(Place place) {
        this.address = place;
    }

    public LookupQualityRating getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(LookupQualityRating lookupQualityRating) {
        this.qualityRating = lookupQualityRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provider provider = (Provider) o;
        if(provider.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, provider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Provider{" +
            "id=" + id +
            ", providerCapacity='" + providerCapacity + "'" +
            ", providerName='" + providerName + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            '}';
    }
}
