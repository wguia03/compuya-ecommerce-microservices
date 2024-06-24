package com.compuya.ecommerce.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findRolesByRoleEnumIn(List<RoleEnum> roleNames);
}
