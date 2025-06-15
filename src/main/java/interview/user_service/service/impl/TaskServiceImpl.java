package interview.user_service.service.impl;

import interview.user_service.dto.request.TaskCreateRequest;
import interview.user_service.dto.request.TaskUpdateRequest;
import interview.user_service.dto.response.TaskResponse;
import interview.user_service.entity.Task;
import interview.user_service.entity.User;
import interview.user_service.exception.CustomException;
import interview.user_service.mapper.TaskMapper;
import interview.user_service.repository.TaskRepository;
import interview.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements interview.user_service.service.TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        User currentUser = getCurrentUser();

        Task task = taskMapper.toEntity(request);
        task.setCreatedBy(currentUser);

        Task savedTask = taskRepository.save(task);
        return taskMapper.toResponse(savedTask);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(taskMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponse updateTaskStatus(Long taskId, TaskUpdateRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException("Task not found", HttpStatus.NOT_FOUND));

        task.setStatus(request.getStatus());
        Task updatedTask = taskRepository.save(task);
        return taskMapper.toResponse(updatedTask);
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
    }
}