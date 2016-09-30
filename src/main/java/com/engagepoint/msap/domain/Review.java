package com.engagepoint.msap.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "content")
    private String content;
    
    @Column(name = "review_date")
    private ZonedDateTime reviewDate;
    
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(ZonedDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        if(review.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + id +
            ", content='" + content + "'" +
            ", reviewDate='" + reviewDate + "'" +
            '}';
    }
}
