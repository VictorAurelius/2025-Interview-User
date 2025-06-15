package interview.user_service.mapper;

import interview.user_service.dto.request.UserRegistrationRequest;
import interview.user_service.dto.response.UserResponse;
import interview.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(UserRegistrationRequest request);

    UserResponse toResponse(User user);
}