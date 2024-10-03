package com.lunastore.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteItemResponse {
    // Getters and Setters
    private boolean success;
    private String message;

    public DeleteItemResponse() {}

    public DeleteItemResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}