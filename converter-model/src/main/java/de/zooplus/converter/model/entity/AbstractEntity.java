package de.zooplus.converter.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by dragan
 */
@MappedSuperclass
public abstract class AbstractEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Integer id;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;

    @PrePersist
    public void prePersist(){
        if(createdOn == null) {
            createdOn = new Date();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
