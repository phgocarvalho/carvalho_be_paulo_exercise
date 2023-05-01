package com.ecore.roles.web.rest;

import com.ecore.roles.service.TeamsService;
import com.ecore.roles.web.TeamsApi;
import com.ecore.roles.web.dto.TeamDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.TeamDto.fromModel;

@RestController
@RequestMapping(value = "/v1/teams")
public class TeamsRestController implements TeamsApi {

    private final TeamsService service;

    public TeamsRestController(TeamsService service) {
        this.service = service;
    }

    @Override
    @GetMapping(
            produces = {"application/json"})
    public ResponseEntity<List<TeamDto>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAll().stream()
                        .map(TeamDto::fromModel)
                        .collect(Collectors.toList()));
    }

    @Override
    @GetMapping(
            path = "/{id}",
            produces = {"application/json"})
    public ResponseEntity<TeamDto> getById(
            @PathVariable UUID id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fromModel(service.getById(id).get()));
    }

}
