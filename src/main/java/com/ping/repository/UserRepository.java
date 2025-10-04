package com.ping.repository;

import com.ping.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    @Query("select u from User u where u.full_name Like %:query% or u.email Like %:query%")
    public List<User>searchUser(@Param("query") String query);

    @Query("SELECT u FROM User u")
    List<User> findTop10Users(Pageable pageable);
}


//1.44.14