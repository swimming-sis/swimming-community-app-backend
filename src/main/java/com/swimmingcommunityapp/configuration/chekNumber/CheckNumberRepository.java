package com.swimmingcommunityapp.configuration.chekNumber;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckNumberRepository extends CrudRepository<CheckNumber,String > {
    Optional<CheckNumber> findByPhoneNumber (String phoneNumber);
}
