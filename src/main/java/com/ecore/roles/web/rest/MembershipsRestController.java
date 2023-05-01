package com.ecore.roles.web.rest;

import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.web.MembershipsApi;
import com.ecore.roles.web.dto.MembershipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ecore.roles.web.dto.MembershipDto.fromModel;

@RestController
@RequestMapping(value = "/v1/roles/memberships")
public class MembershipsRestController implements MembershipsApi {

    private final MembershipsService service;

    @Autowired
    public MembershipsRestController(MembershipsService service) {
        this.service = service;
    }

    @Override
    @PostMapping(
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<MembershipDto> assignRoleAndCreate(
            @NotNull @Valid @RequestBody MembershipDto membershipDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fromModel(service.assignRoleAndCreate(membershipDto.toModel())));
    }

    @Override
    @GetMapping(
            path = "/search",
            produces = {"application/json"})
    public ResponseEntity<List<MembershipDto>> getAllByRoleId(
            @RequestParam UUID roleId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllByRoleId(roleId)
                        .stream()
                        .map(membership -> fromModel(membership))
                        .collect(Collectors.toList()));
    }

}
