package org.ganapati.project.ecommerce.repository;

import org.ganapati.project.ecommerce.entity.Roles;
import org.ganapati.project.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles,Long> {
    List<Roles> findByUser(User user);
}
