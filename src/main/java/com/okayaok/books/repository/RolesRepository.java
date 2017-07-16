package com.okayaok.books.repository;

import com.okayaok.books.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author okayaok
 * 2017/7/16.
 */
@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
}
