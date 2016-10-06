package com.engagepoint.msap.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SupportedSpecialNeed.
 */
@Entity
@Table(name = "supported_special_need")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "supportedspecialneed")
public class SupportedSpecialNeed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference("supportedSpecialNeeds")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToOne
    private LookupSpecialNeedType specialNeedType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LookupSpecialNeedType getSpecialNeedType() {
        return specialNeedType;
    }

    public void setSpecialNeedType(LookupSpecialNeedType lookupSpecialNeedType) {
        this.specialNeedType = lookupSpecialNeedType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SupportedSpecialNeed supportedSpecialNeed = (SupportedSpecialNeed) o;
        if(supportedSpecialNeed.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, supportedSpecialNeed.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SupportedSpecialNeed{" +
            "id=" + id +
            '}';
    }
}
