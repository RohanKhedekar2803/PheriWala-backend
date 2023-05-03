package com.example.StreetvendorBackend.Repositrory;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.StreetvendorBackend.Entity.User;
import com.example.StreetvendorBackend.Entity.Vendor;

public interface UserRepository  extends CrudRepository<User, Long>{

	Optional<User> findByUsername(String username);

	Optional<User> findByUsernameAndPassword(String username, String password);




}
