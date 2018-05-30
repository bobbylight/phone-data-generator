package com.sas.fcs.toc.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;

@Entity
@JsonPropertyOrder({ "number", "type" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Phone {

    @Id
    @Column(nullable = false, updatable = false, length = ModelConstants.PHONE_NUMBER_MAX)
    private String number;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false, length = ModelConstants.PHONE_TYPE_MAX)
    private PhoneType type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("type", type)
                .append("number", number)
                .toString();
    }
}
