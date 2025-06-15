package interview.user_service.dto.request;

import interview.user_service.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateRequest {

    @NotNull(message = "Status is required")
    private TaskStatus status;
}