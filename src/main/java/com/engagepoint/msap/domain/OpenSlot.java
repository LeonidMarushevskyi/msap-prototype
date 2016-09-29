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
 * A OpenSlot.
 */
@Entity
@Table(name = "open_slot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "openslot")
public class OpenSlot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "open_slots")
    private Integer openSlots;
    
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

    public Integer getOpenSlots() {
        return openSlots;
    }
    
    public void setOpenSlots(Integer openSlots) {
        this.openSlots = openSlots;
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
        OpenSlot openSlot = (OpenSlot) o;
        if(openSlot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, openSlot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OpenSlot{" +
            "id=" + id +
            ", openSlots='" + openSlots + "'" +
            '}';
    }
}
