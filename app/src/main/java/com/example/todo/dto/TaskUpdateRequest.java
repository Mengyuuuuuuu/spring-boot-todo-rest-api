package com.example.todo.dto;

import jakarta.validation.constraints.Size;

public class TaskUpdateRequest {

    @Size(max = 200)
    private String title;

    private Boolean completed;

    public String getTitle() { return title; }
    public Boolean getCompleted() { return completed; }

    public void setTitle(String title) { this.title = title; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}