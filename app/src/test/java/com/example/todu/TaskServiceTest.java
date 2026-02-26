package com.example.todu;

import com.example.todu.dto.TaskCreateRequest;
import com.example.todu.dto.TaskUpdateRequest;
import com.example.todu.model.Task;
import com.example.todu.repository.TaskRepository;
import com.example.todu.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskService service;

    @Autowired
    private TaskRepository repo;

    @Test
    void createTrimsTitle() {
        TaskCreateRequest req = new TaskCreateRequest();
        req.setTitle("  hello  ");
        Task t = service.create(req);

        assertEquals("hello", t.getTitle());
    }

    @Test
    void updateNonExistingThrows404() {
        TaskUpdateRequest req = new TaskUpdateRequest();
        req.setCompleted(true);

        assertThrows(ResponseStatusException.class, () -> service.update(999999L, req));
    }
}