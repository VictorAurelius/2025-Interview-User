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
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "email", source = "email")
    User toEntity(UserRegistrationRequest request);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "role", source = "role")
    UserResponse toResponse(User user);
}