package com.example.todo;

import com.example.todo.dto.TaskCreateRequest;
import com.example.todo.dto.TaskUpdateRequest;
import com.example.todo.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerIT {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void crudFlowWorks() {
        // 1) Create
        TaskCreateRequest create = new TaskCreateRequest();
        create.setTitle("CI Test Task");

        ResponseEntity<Task> createdRes = rest.postForEntity("/api/tasks", create, Task.class);
        assertEquals(HttpStatus.CREATED, createdRes.getStatusCode());
        assertNotNull(createdRes.getBody());
        Long id = createdRes.getBody().getId();
        assertNotNull(id);
        assertEquals("CI Test Task", createdRes.getBody().getTitle());
        assertFalse(createdRes.getBody().isCompleted());

        // 2) Get by id
        ResponseEntity<Task> getRes = rest.getForEntity("/api/tasks/" + id, Task.class);
        assertEquals(HttpStatus.OK, getRes.getStatusCode());
        assertNotNull(getRes.getBody());
        assertEquals(id, getRes.getBody().getId());

        // 3) Patch (complete=true)
        TaskUpdateRequest update = new TaskUpdateRequest();
        update.setCompleted(true);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TaskUpdateRequest> patchEntity = new HttpEntity<>(update, headers);

        ResponseEntity<Task> patchRes = rest.exchange("/api/tasks/" + id, HttpMethod.PATCH, patchEntity, Task.class);
        assertEquals(HttpStatus.OK, patchRes.getStatusCode());
        assertNotNull(patchRes.getBody());
        assertTrue(patchRes.getBody().isCompleted());

        // 4) Delete
        ResponseEntity<Void> delRes = rest.exchange("/api/tasks/" + id, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, delRes.getStatusCode());

        // 5) Get after delete -> 404
        ResponseEntity<String> afterDel = rest.getForEntity("/api/tasks/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, afterDel.getStatusCode());
    }
}