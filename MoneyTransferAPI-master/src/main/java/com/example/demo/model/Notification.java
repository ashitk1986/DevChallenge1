package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

// Lombok annotations
// Causes Lombok to generate getter() methods.
@Getter
// Causes Lombok to generate the toString() method.
@ToString
// Causes Lombok to generate a constructor with 1 parameter for each field in your class.
@AllArgsConstructor
public class Notification {

    @NonNull
    @JsonProperty("message")
    final String message;
}