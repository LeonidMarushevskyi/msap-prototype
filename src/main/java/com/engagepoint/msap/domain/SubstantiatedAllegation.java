package com.engagepoint.msap.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * A SubstantiatedAllegation.
 */
@Entity
@Table(name = "substantiated_allegation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "substantiatedallegation")
public class SubstantiatedAllegation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "severity")
    private String severity;

    @Column(name = "allegation_date")
    private LocalDate allegationDate;

    @ManyToOne
    @JsonBackReference("substantiatedAllegations")
    @JoinColumn(name = "provider_id")
    private Provider provider;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDate getAllegationDate() {
        return allegationDate;
    }

    public void setAllegationDate(LocalDate allegationDate) {
        this.allegationDate = allegationDate;
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
        SubstantiatedAllegation substantiatedAllegation = (SubstantiatedAllegation) o;
        if(substantiatedAllegation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, substantiatedAllegation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubstantiatedAllegation{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", severity='" + severity + "'" +
            ", allegationDate='" + allegationDate + "'" +
            '}';
    }
}
