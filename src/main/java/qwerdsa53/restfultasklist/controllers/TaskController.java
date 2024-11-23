package qwerdsa53.restfultasklist.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import qwerdsa53.restfultasklist.security.CustomUserDetails;
import qwerdsa53.restfultasklist.task.Task;
import qwerdsa53.restfultasklist.task.TaskService;
import qwerdsa53.restfultasklist.user.UserRepo;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserRepo userRepo;

    @GetMapping()
    public List<Task> findAllForCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            Long userId = userDetails.getId();
            return taskService.findAllForCurrentUser(userId);
        } else {
            throw new IllegalStateException("Authentication principal is not an instance of CustomUserDetails");
        }
    }

    @PostMapping
    public ResponseEntity<String> addTaskForCurrentUser(@RequestBody Task task) {
        Long userId = getUserId();
        try {
            taskService.addTaskForCurrentUser(task, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Task created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating task: " + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<String> updateTask(@RequestBody Task task) {
        Long userId = getUserId();
        try {
            taskService.updateTask(task, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Task updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input: " + e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating task: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        Long userId = getUserId();
        try {
            taskService.deleteTask(id, userId);
            return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found or access denied.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting task: " + e.getMessage());
        }
    }


    private Long getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getId();
        } else {
            throw new IllegalStateException("Authentication principal is not an instance of CustomUserDetails");
        }
    }


}
