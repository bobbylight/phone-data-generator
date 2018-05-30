package com.sas.fcs.toc.repository;

import com.sas.fcs.toc.model.PhoneCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneCallRepository extends JpaRepository<PhoneCall, String> {

    @Query(nativeQuery = true,
            value = "select count(1) from phone_call where source_number = :number or dest_number = :number")
    public int getCallInvolvementCount(@Param("number") String number);
}
