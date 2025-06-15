package interview.user_service.controller;

import interview.user_service.dto.request.TaskCreateRequest;
import interview.user_service.dto.request.TaskUpdateRequest;
import interview.user_service.dto.response.ApiResponse;
import interview.user_service.dto.response.TaskResponse;
import interview.user_service.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(
            @Valid @RequestBody TaskCreateRequest request) {
        TaskResponse taskResponse = taskService.createTask(request);
        return ResponseEntity.ok(ApiResponse.success("Task created successfully", taskResponse));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(ApiResponse.success("Tasks retrieved successfully", tasks));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request) {
        TaskResponse taskResponse = taskService.updateTaskStatus(id, request);
        return ResponseEntity.ok(ApiResponse.success("Task status updated successfully", taskResponse));
    }
}