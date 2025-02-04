package com.infiniteloop.springprojectmongodb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.infiniteloop.springprojectmongodb.repositories.AccessedRepo;
import com.infiniteloop.springprojectmongodb.models.Accessed;
import com.infiniteloop.springprojectmongodb.repositories.QuestionsRepo;

@RestController
@RequestMapping(value = "/accessed")
public class AccessedController {

    @Autowired
    AccessedRepo accessedRepo;

    @Autowired
    QuestionsRepo questionsRepo;

    // Endpoint to check if Accessed record is answered
    @CrossOrigin(origins = {"http://localhost:5173","http://localhost:5500"})
    @GetMapping(value = "/isAnswered/{login}", produces = "application/json")
    public ResponseEntity<String> isAnswered(@PathVariable String login) {
        
        try {
            Accessed accessed = accessedRepo.findByLogin(login).orElse(null);

            if (accessed != null) {
                boolean result = accessed.getIsAnswered();
                return ResponseEntity.ok("{\"isAnswered\": " + result + "}");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"No resource Found\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while processing the request\"}");
        }
    }

    // Endpoint to get the score of the Accessed record
    @CrossOrigin(origins = {"http://localhost:5173","http://localhost:5500"})
    @GetMapping(value = "/finalscore/{login}", produces = "application/json")
    public ResponseEntity<String> getFinalScore(@PathVariable String login) {
        try {
            Accessed accessed = accessedRepo.findByLogin(login).orElse(null);
            if (accessed != null) {
                int result = accessed.getScore();
                return ResponseEntity.ok("{\"score\": " + result + "}");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"No resource Found\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"An error occurred while processing the request\"}");
        }
}}
