package com.engagepoint.msap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LookupSpecialNeedGroup.
 */
@Entity
@Table(name = "lookup_special_need_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lookupspecialneedgroup")
public class LookupSpecialNeedGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "group_code", nullable = false)
    private Integer groupCode;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupCode() {
        return groupCode;
    }
    
    public void setGroupCode(Integer groupCode) {
        this.groupCode = groupCode;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LookupSpecialNeedGroup lookupSpecialNeedGroup = (LookupSpecialNeedGroup) o;
        if(lookupSpecialNeedGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lookupSpecialNeedGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LookupSpecialNeedGroup{" +
            "id=" + id +
            ", groupCode='" + groupCode + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
