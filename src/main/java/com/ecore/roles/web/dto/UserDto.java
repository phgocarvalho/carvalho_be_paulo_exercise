package com.ecore.roles.web.dto;

import com.ecore.roles.client.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserDto {

    @JsonProperty(value = "id")
    private UUID id;

    @JsonProperty(value = "firstName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstName;

    @JsonProperty(value = "lastName")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonProperty(value = "displayName")
    private String displayName;

    @JsonProperty(value = "avatarUrl")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatarUrl;

    @JsonProperty(value = "location")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;

    public static UserDto fromModel(User user) {

        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .displayName(user.getDisplayName())
                .avatarUrl(user.getAvatarUrl())
                .location(user.getLocation())
                .build();
    }
}
