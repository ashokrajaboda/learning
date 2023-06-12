package com.gagana.hospital.api.repository;

import com.gagana.hospital.api.domain.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<User,Long> {//ReactiveCrudRepository<User,Long> {
    //@Query("select * from User where username = :username")
    Mono<User> findByUsername(String username);
}
