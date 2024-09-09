package com.nusyn.license.main.access.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nusyn.license.main.access.pojo.Role;





@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String roleName);
}
