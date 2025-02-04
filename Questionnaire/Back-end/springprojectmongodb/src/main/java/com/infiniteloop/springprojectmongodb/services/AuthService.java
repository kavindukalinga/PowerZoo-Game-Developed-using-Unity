package com.infiniteloop.springprojectmongodb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.infiniteloop.springprojectmongodb.payloads.SignUpDto;
import com.infiniteloop.springprojectmongodb.models.User;
import com.infiniteloop.springprojectmongodb.models.Accessed;
import com.infiniteloop.springprojectmongodb.exceptions.InvalidJwtException;
import com.infiniteloop.springprojectmongodb.repositories.UserRepo;
import com.infiniteloop.springprojectmongodb.repositories.AccessedRepo;

@Service
public class AuthService implements UserDetailsService {

  @Autowired
  UserRepo repository;

  @Autowired
  AccessedRepo accessedRepo;

  // Method to load user by username (used by Spring Security)
  @Override
  public UserDetails loadUserByUsername(String username) {
    var user = repository.findByLogin(username);
    return user;
  }

  // Method to register a new user
  public UserDetails signUp(SignUpDto data) throws InvalidJwtException {

    // Check if username already exists
    if (repository.findByLogin(data.login()) != null) {
      throw new InvalidJwtException("Username already exists");
    }

    // Encrypt password
    String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    Boolean isAnswered = false;
    Integer score = 0;  
    String accessToken = "";
    String login = data.login();

    Accessed newAccessed = new Accessed(isAnswered, score, accessToken, login);
    accessedRepo.save(newAccessed);

    // Create new user
    User newUser = new User(data.login(), encryptedPassword, data.role());

    // Save the new user
    return repository.save(newUser);
  }
}
