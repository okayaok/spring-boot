package com.okayaok.books.service.user;

import com.okayaok.books.domain.User;
import com.okayaok.books.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户登陆时，权限验证
 *
 * @author hang_xiao
 *         2017/7/17.
 */
@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    /**
     * 添加用户所拥有的权限
     *
     * @param username 用户名
     * @return 用户信息
     * @throws UsernameNotFoundException 用户名不存在异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user != null) {
            user.getAuthorities();
        }
        return user;
    }
}
