package com.amlan.ooptwitter;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ErrorJSONFormatter {
    public static ResponseEntity<Object> errorJSONResponse(String message) {
        Map<String, String> error = Map.of("Error", message);
        return ResponseEntity.badRequest().body(error);
    }

}
