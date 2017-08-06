package com.okayaok.books.repository;

import com.okayaok.books.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author hang_xiao
 *         2017/7/8.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    /**
     * 根据用户名查找用户是否存在
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 根据用户邮箱号查找用户信息
     *
     * @param email 邮箱
     * @return 用户
     */
    User findByEmail(String email);

    /**
     * 根据用户名和密码判断用户是否存在
     *
     * @param username 用户名
     * @param password 密码
     * @return true:存在 false:不存在
     */
    boolean existsByUsernameAndPassword(String username, String password);
}
