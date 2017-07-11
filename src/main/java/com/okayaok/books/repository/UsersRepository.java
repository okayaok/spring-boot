package com.okayaok.books.repository;

import com.okayaok.books.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    boolean findByUsernameAndPassword(String username, String password);
}
