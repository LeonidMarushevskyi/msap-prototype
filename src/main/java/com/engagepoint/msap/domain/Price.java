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
    private Integer minimalPrice;
    
    @Column(name = "maximum_price")
    private Integer maximumPrice;
    
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

    public Integer getMinimalPrice() {
        return minimalPrice;
    }
    
    public void setMinimalPrice(Integer minimalPrice) {
        this.minimalPrice = minimalPrice;
    }

    public Integer getMaximumPrice() {
        return maximumPrice;
    }
    
    public void setMaximumPrice(Integer maximumPrice) {
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
