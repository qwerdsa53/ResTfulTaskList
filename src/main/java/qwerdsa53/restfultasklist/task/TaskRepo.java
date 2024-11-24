package qwerdsa53.restfultasklist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TaskRepo extends JpaRepository<Task, Long> {
    @Query("SELECT e FROM Task e WHERE e.user.id = :userId")
    List<Task> findAllForCurrentUser(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE Task t SET t.title = :title, t.description = :description, t.completed = :completed, " +
            "t.updatedAt = :updatedAt WHERE t.id = :taskId AND t.user.id = :userId")
    int updateTaskByIdAndUserId(
            @Param("taskId") Long taskId,
            @Param("userId") Long userId,
            @Param("title") String title,
            @Param("description") String description,
            @Param("completed") boolean completed,
            @Param("updatedAt") LocalDateTime updatedAt
    );


    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = :taskId AND t.user.id = :userId")
    int deleteByIdAndUserId(
            @Param("taskId") Long taskId,
            @Param("userId") Long userId
    );

}
