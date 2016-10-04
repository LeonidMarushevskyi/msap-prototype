package com.engagepoint.msap.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LookupSpecialNeedType.
 */
@Entity
@Table(name = "lookup_special_need_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lookupspecialneedtype")
public class LookupSpecialNeedType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToOne
    private LookupSpecialNeedGroup specialNeedGroup;

    @JsonBackReference("specialNeeds")
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LookupSpecialNeedGroup getSpecialNeedGroup() {
        return specialNeedGroup;
    }

    public void setSpecialNeedGroup(LookupSpecialNeedGroup lookupSpecialNeedGroup) {
        this.specialNeedGroup = lookupSpecialNeedGroup;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LookupSpecialNeedType lookupSpecialNeedType = (LookupSpecialNeedType) o;
        if(lookupSpecialNeedType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lookupSpecialNeedType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LookupSpecialNeedType{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
