package org.example.plantsmap.controller;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/comment", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class CommentController {

    @PostMapping
    public ResponseEntity addComment(@CookieValue("user") String cookie, @RequestBody Comment comment) {
        // todo
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{plantId}")
    public ResponseEntity getComment(@CookieValue("user") String cookie, @PathVariable("plantId") String plantId) {
       //todo
        return ResponseEntity.ok().build();
    }
}
