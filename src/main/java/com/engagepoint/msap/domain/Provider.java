package com.engagepoint.msap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "is_open_overnight")
    private Boolean isOpenOvernight;

    @Column(name = "number_of_complains")
    private Integer numberOfComplains;

    @Column(name = "number_of_visits")
    private Integer numberOfVisits;

    @Column(name = "last_visit")
    private LocalDate lastVisit;

    @Column(name = "description")
    private String description;

    @Column(name = "is_full_day")
    private Boolean isFullDay;

    @Column(name = "is_after_school")
    private Boolean isAfterSchool;

    @Column(name = "is_before_school")
    private Boolean isBeforeSchool;

    @Column(name = "is_weekend_care")
    private Boolean isWeekendCare;

    @OneToOne
    private LookupLicenseType licenseType;

    @OneToOne
    private LookupProviderType providerType;

    @OneToOne
    private Place address;

    @OneToOne
    private LookupQualityRating qualityRating;

    @JsonManagedReference("openSlots")
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OpenSlot> openSlots = new HashSet<>();

    @JsonManagedReference("schedules")
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LookupSpecialNeedType> specialNeeds = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Review> reviews = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Price> prices = new HashSet<>();

    @JsonManagedReference("substantiatedAllegations")
    @OneToMany(mappedBy = "provider", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SubstantiatedAllegation> substantiatedAllegations = new HashSet<>();

    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LookupLanguage> supportedLanguages = new HashSet<>();

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

    public Boolean getIsOpenOvernight() {
        return isOpenOvernight;
    }

    public void setIsOpenOvernight(Boolean isOpenOvernight) {
        this.isOpenOvernight = isOpenOvernight;
    }

    public Integer getNumberOfComplains() {
        return numberOfComplains;
    }

    public void setNumberOfComplains(Integer numberOfComplains) {
        this.numberOfComplains = numberOfComplains;
    }

    public Integer getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(Integer numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsFullDay() {
        return isFullDay;
    }

    public void setIsFullDay(Boolean isFullDay) {
        this.isFullDay = isFullDay;
    }

    public Boolean getIsAfterSchool() {
        return isAfterSchool;
    }

    public void setIsAfterSchool(Boolean isAfterSchool) {
        this.isAfterSchool = isAfterSchool;
    }

    public Boolean getIsBeforeSchool() {
        return isBeforeSchool;
    }

    public void setIsBeforeSchool(Boolean isBeforeSchool) {
        this.isBeforeSchool = isBeforeSchool;
    }

    public Boolean getIsWeekendCare() {
        return isWeekendCare;
    }

    public void setIsWeekendCare(Boolean isWeekendCare) {
        this.isWeekendCare = isWeekendCare;
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

    public Set<OpenSlot> getOpenSlots() {
        return openSlots;
    }

    public void setOpenSlots(Set<OpenSlot> openSlots) {
        this.openSlots = openSlots;
    }

    public Set<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Set<LookupSpecialNeedType> getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(Set<LookupSpecialNeedType> lookupSpecialNeedTypes) {
        this.specialNeeds = lookupSpecialNeedTypes;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Price> getPrices() {
        return prices;
    }

    public void setPrices(Set<Price> prices) {
        this.prices = prices;
    }

    public Set<SubstantiatedAllegation> getSubstantiatedAllegations() {
        return substantiatedAllegations;
    }

    public void setSubstantiatedAllegations(Set<SubstantiatedAllegation> substantiatedAllegations) {
        this.substantiatedAllegations = substantiatedAllegations;
    }

    public Set<LookupLanguage> getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(Set<LookupLanguage> lookupLanguages) {
        this.supportedLanguages = lookupLanguages;
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
            ", isOpenOvernight='" + isOpenOvernight + "'" +
            ", numberOfComplains='" + numberOfComplains + "'" +
            ", numberOfVisits='" + numberOfVisits + "'" +
            ", lastVisit='" + lastVisit + "'" +
            ", description='" + description + "'" +
            ", isFullDay='" + isFullDay + "'" +
            ", isAfterSchool='" + isAfterSchool + "'" +
            ", isBeforeSchool='" + isBeforeSchool + "'" +
            ", isWeekendCare='" + isWeekendCare + "'" +
            '}';
    }
}
