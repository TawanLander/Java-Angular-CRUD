package lander.tawan.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lander.tawan.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDataResponse(
    UUID id,

    String name,

    String phone,

    String email,

    @JsonProperty("created_at")
    LocalDateTime createdAt,

    @JsonProperty("deleted_at")
    LocalDateTime deletedAt
){
    public static UserDataResponse from(UserEntity user) {
        return new UserDataResponse(
                user.getId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDeletedAt()
        );
    }
}
