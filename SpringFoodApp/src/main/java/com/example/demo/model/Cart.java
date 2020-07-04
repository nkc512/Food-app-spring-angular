package com.example.demo.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {
    @Transient
    public static final String SEQUENCE_NAME = "cart_sequence";

}
