package com.ecore.roles.client.model;

import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.model.Membership;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Team {

    @Id
    @JsonProperty(value = "id")
    private UUID id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "teamLeadId")
    private UUID teamLeadId;

    @JsonProperty(value = "teamMemberIds")
    private List<UUID> teamMemberIds;

    public void validateTeamMember(Membership membership) {
        teamMemberIds.stream()
                .filter(teamMemberId -> teamMemberId.equals(membership.getUserId()))
                .findFirst()
                .orElseThrow(() -> new InvalidArgumentException(Membership.class));
    }
}
