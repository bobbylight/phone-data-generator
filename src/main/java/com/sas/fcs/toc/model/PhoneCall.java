package com.sas.fcs.toc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonPropertyOrder({ "sourceNumber", "destNumber", "time", "duration" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_call_id")
    private Long id;

    @Column(nullable = false, updatable = false, length = ModelConstants.PHONE_NUMBER_MAX)
    private String sourceNumber;

    @Column(nullable = false, updatable = false, length = ModelConstants.PHONE_NUMBER_MAX)
    private String destNumber;

    @Column(nullable = false, updatable = false)
    private Date time;

    @Column(nullable = false, updatable = false)
    private Long duration;

    public String getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(String sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public String getDestNumber() {
        return destNumber;
    }

    public void setDestNumber(String destNumber) {
        this.destNumber = destNumber;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
