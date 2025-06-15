package interview.user_service.mapper;

import interview.user_service.dto.request.TaskCreateRequest;
import interview.user_service.dto.response.TaskResponse;
import interview.user_service.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Task toEntity(TaskCreateRequest request);

    @Mapping(target = "createdBy", source = "createdBy.username")
    TaskResponse toResponse(Task task);
}