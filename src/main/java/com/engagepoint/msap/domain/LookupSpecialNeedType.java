package com.engagepoint.msap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    @Column(name = "code", nullable = false)
    private Integer code;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @OneToOne
    private LookupSpecialNeedGroup specialNeedGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }
    
    public void setCode(Integer code) {
        this.code = code;
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
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
