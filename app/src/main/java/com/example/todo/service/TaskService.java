package com.example.todo.service;

import com.example.todo.dto.TaskCreateRequest;
import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public List<Task> findAll() {
        return repo.findAll();
    }

    public Task findById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + id));
    }

    public Task create(TaskCreateRequest req) {
        Task task = new Task(req.getTitle().trim());
        return repo.save(task);
    }

    public Task update(Long id, TaskUpdateRequest req) {
        Task task = findById(id);

        if (req.getTitle() != null) {
            String t = req.getTitle().trim();
            if (!t.isEmpty()) task.setTitle(t);
        }
        if (req.getCompleted() != null) {
            task.setCompleted(req.getCompleted());
        }

        return repo.save(task);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found: " + id);
        }
        repo.deleteById(id);
    }
}