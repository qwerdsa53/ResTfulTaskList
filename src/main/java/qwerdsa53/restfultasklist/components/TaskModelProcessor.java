package qwerdsa53.restfultasklist.components;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import qwerdsa53.restfultasklist.entity.Task;

@Component
public class TaskModelProcessor implements RepresentationModelProcessor<EntityModel<Task>> {
    @Override
    public @NotNull EntityModel<Task> process(@NotNull EntityModel<Task> model) {
        Task task = model.getContent();
        if (task != null) {
            model.add(Link.of("http://localhost:8080/custom-tasks/" + task.getId()).withRel("custom-task-details"));
        }
        return model;
    }
}