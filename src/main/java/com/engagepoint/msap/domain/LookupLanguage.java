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
 * A LookupLanguage.
 */
@Entity
@Table(name = "lookup_language")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lookuplanguage")
public class LookupLanguage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 2)
    @Column(name = "code", length = 2, nullable = false)
    private String code;
    
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
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
        LookupLanguage lookupLanguage = (LookupLanguage) o;
        if(lookupLanguage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lookupLanguage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LookupLanguage{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
