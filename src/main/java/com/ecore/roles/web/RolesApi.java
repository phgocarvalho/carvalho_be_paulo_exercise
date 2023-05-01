package com.ecore.roles.web;

import com.ecore.roles.web.dto.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RolesApi {

    ResponseEntity<RoleDto> create(
            RoleDto role);

    ResponseEntity<List<RoleDto>> getAll();

    ResponseEntity<RoleDto> getById(
            UUID id);

    ResponseEntity<List<RoleDto>> getAllByUserIdAndTeamId(
            UUID userId,
            UUID teamId);
}
