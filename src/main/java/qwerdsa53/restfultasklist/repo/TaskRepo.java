package qwerdsa53.restfultasklist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import qwerdsa53.restfultasklist.entity.Task;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("SELECT e FROM Task e WHERE e.user.id = :userId")
    List<Task> findAllForCurrentUser(@Param("userId") Long userId);
}
