package com.sas.fcs.toc.repository;

import com.sas.fcs.toc.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneRepository extends JpaRepository<Phone, String> {

    @Query(nativeQuery = true, value = "select number from phone offset :offset limit 1")
    public String getNumber(@Param("offset") int offset);
}
