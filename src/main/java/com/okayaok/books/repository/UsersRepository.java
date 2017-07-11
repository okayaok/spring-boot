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

    /**
     * 根据用户名和密码判断用户是否存在
     *
     * @param username 用户名
     * @param password 密码
     * @return true:存在 false:不存在
     */
    boolean existsByUsernameAndPassword(String username, String password);
}
