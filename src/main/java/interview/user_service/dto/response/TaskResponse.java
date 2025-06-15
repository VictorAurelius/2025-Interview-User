package interview.user_service.dto.response;

import interview.user_service.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}