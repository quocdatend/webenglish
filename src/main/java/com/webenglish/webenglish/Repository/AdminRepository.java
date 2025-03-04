package com.webenglish.webenglish.Repository;

import com.webenglish.webenglish.Model.Admins;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admins, Long> {
    @Query("SELECT a FROM Admins a WHERE a.username = :username")
    public Admins getAdminByUsername(@Param("username") String username);
    Admins findByUsername(String username);
    Admins findByEmail(String email);
}
