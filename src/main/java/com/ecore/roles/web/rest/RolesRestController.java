package com.ecore.roles.web.rest;

import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.RolesApi;
import com.ecore.roles.web.dto.RoleDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.RoleDto.fromModel;

@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController implements RolesApi {

    private final RolesService service;

    public RolesRestController(RolesService service) {
        this.service = service;
    }

    @Override
    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<RoleDto> create(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fromModel(service.create(role.toModel())));
    }

    @Override
    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll()
                        .stream()
                        .map(role -> fromModel(role))
                        .collect(Collectors.toList()));
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = {"application/json"})
    public ResponseEntity<RoleDto> getById(
            @PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(service.getById(id)));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getAllByUserIdAndTeamId(
            @RequestParam UUID userId,
            @RequestParam UUID teamId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllByUserIdAndTeamId(userId, teamId)
                        .stream()
                        .map(role -> fromModel(role))
                        .collect(Collectors.toList()));
    }

}
