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
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "schedule")
public class Schedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "open_hour")
    private String openHour;
    
    @Column(name = "close_hour")
    private String closeHour;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToOne
    private LookupDayOfWeek dayOfWeek;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenHour() {
        return openHour;
    }
    
    public void setOpenHour(String openHour) {
        this.openHour = openHour;
    }

    public String getCloseHour() {
        return closeHour;
    }
    
    public void setCloseHour(String closeHour) {
        this.closeHour = closeHour;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LookupDayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(LookupDayOfWeek lookupDayOfWeek) {
        this.dayOfWeek = lookupDayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Schedule schedule = (Schedule) o;
        if(schedule.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, schedule.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Schedule{" +
            "id=" + id +
            ", openHour='" + openHour + "'" +
            ", closeHour='" + closeHour + "'" +
            '}';
    }
}
