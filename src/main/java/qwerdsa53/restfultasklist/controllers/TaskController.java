package qwerdsa53.restfultasklist.controllers;

import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import qwerdsa53.restfultasklist.entity.Task;
import qwerdsa53.restfultasklist.repo.TaskRepo;
import qwerdsa53.restfultasklist.repo.UserRepo;
import qwerdsa53.restfultasklist.security.CustomUserDetails;
import qwerdsa53.restfultasklist.services.TaskService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserRepo userRepo;
    @GetMapping()
    public List<Task> findAllForCurrentUser(Principal principal) {
        // Получаем Authentication из SecurityContextHolder
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, что principal является объектом CustomUserDetails
        if (auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            Long userId = userDetails.getId(); // Извлекаем ID пользователя

            // Вызываем TaskService с ID пользователя и диапазоном дат
            return taskService.findAllForCurrentUser(userId);
        } else {
            throw new IllegalStateException("Authentication principal is not an instance of CustomUserDetails");
        }
    }

    @PostMapping()
    public Task addTaskForCurrentUser(@RequestBody Task task){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, что principal является объектом CustomUserDetails
        if (auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            Long userId = userDetails.getId(); // Извлекаем ID пользователя

            // Вызываем TaskService с ID пользователя и диапазоном дат
            task.setUser(userRepo.findById(userId).orElseThrow());
            return taskService.addTaskForCurrentUser(task);
        } else {
            throw new IllegalStateException("Authentication principal is not an instance of CustomUserDetails");
        }
    }
}
