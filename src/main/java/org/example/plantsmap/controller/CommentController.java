package org.example.plantsmap.controller;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Comment;
import org.example.plantsmap.exception.InvalidDataException;
import org.example.plantsmap.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/comment", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    public ResponseEntity<Object> addComment(@RequestBody Comment comment) {
        try {
            service.save(comment);
            return ResponseEntity.ok().build();
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping(value = "/{plantId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable("plantId") Integer plantId) {
        try {
            return ResponseEntity.ok(service.getByPlantId(plantId));
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
