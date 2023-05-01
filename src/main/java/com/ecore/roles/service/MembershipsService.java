package com.ecore.roles.service;

import com.ecore.roles.model.Membership;

import java.util.List;
import java.util.UUID;

public interface MembershipsService {

    Membership assignRoleAndCreate(Membership membership);

    List<Membership> getAllByRoleId(UUID roleId);

    List<Membership> getAllByUserIdAndTeamId(UUID userId, UUID teamId);
}
