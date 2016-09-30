package com.engagepoint.msap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Price.
 */
@Entity
@Table(name = "price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "price")
public class Price implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "minimal_price")
    private Double minimalPrice;
    
    @Column(name = "maximum_price")
    private Double maximumPrice;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToOne
    private LookupAgeGroups ageGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinimalPrice() {
        return minimalPrice;
    }
    
    public void setMinimalPrice(Double minimalPrice) {
        this.minimalPrice = minimalPrice;
    }

    public Double getMaximumPrice() {
        return maximumPrice;
    }
    
    public void setMaximumPrice(Double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LookupAgeGroups getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(LookupAgeGroups lookupAgeGroups) {
        this.ageGroup = lookupAgeGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price = (Price) o;
        if(price.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, price.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Price{" +
            "id=" + id +
            ", minimalPrice='" + minimalPrice + "'" +
            ", maximumPrice='" + maximumPrice + "'" +
            '}';
    }
}
