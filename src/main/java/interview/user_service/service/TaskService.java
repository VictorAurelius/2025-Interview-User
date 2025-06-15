package interview.user_service.service;

import interview.user_service.dto.request.TaskCreateRequest;
import interview.user_service.dto.request.TaskUpdateRequest;
import interview.user_service.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskCreateRequest request);

    List<TaskResponse> getAllTasks();

    TaskResponse updateTaskStatus(Long taskId, TaskUpdateRequest request);
}