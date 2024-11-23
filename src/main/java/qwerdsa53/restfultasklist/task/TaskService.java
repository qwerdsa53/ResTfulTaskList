package qwerdsa53.restfultasklist.task;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import qwerdsa53.restfultasklist.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepo taskRepo;

    public List<Task> findAllForCurrentUser(Long id) {
        return taskRepo.findAllForCurrentUser(id);
    }

    public void addTaskForCurrentUser(Task task, Long userId) {
        User user = new User();
        user.setId(userId);
        task.setUser(user);
        taskRepo.save(task);
    }

    @Transactional
    public void updateTask(Task task, Long userId) {
        if (task.getId() == null) {
            throw new IllegalArgumentException("Task ID must not be null");
        }

        LocalDateTime updatedAt = LocalDateTime.now();

        log.debug("Updating task with ID: {}, User ID: {}, Title: {}, Description: {}, Completed: {}, UpdatedAt: {}",
                task.getId(), userId, task.getTitle(), task.getDescription(), task.isCompleted(), updatedAt);

        int updatedRows = taskRepo.updateTaskByIdAndUserId(
                task.getId(),
                userId,
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                updatedAt
        );

        if (updatedRows == 0) {
            throw new NoSuchElementException("Task not found or does not belong to the user");
        }
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = new Task();
        task.setId(taskId);

        User user = new User();
        user.setId(userId);
        task.setUser(user);

        taskRepo.delete(task);
    }


}
