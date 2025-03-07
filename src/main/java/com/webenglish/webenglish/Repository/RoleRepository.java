package com.webenglish.webenglish.Repository;

import com.webenglish.webenglish.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleById(Long roleId);
}
