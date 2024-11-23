package qwerdsa53.restfultasklist.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import qwerdsa53.restfultasklist.entity.Task;
import qwerdsa53.restfultasklist.repo.TaskRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    //todo
    private final TaskRepo taskRepo;
    public List<Task> findAllForCurrentUser(Long id){
        return taskRepo.findAllForCurrentUser(id);
    }
    public Task addTaskForCurrentUser(Task task){
        taskRepo.save(task);
        return task;
    }

}
