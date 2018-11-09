package com.userfront.service;

import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;

import java.util.List;
import java.util.Set;

public interface UserService {

    User save(User user);
    List<User> findAll();
    User findByUsername(String username);
    User findByEmail(String email);
    boolean checkUserExist(String username, String email);
    boolean checkUsernameExist(String username);
    boolean checkEmailExist(String email);
    User createUser(User user, Set<UserRole> userRoles);
    void enabledUser(String username);
    void disableUser(String username);
}
