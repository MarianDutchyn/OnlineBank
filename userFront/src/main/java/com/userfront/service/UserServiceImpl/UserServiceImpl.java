package com.userfront.service.UserServiceImpl;

import com.userfront.Repository.RoleRepository;
import com.userfront.Repository.UserRepository;
import com.userfront.domain.User;
import com.userfront.domain.security.UserRole;
import com.userfront.service.AccountService;
import com.userfront.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(User user, Set<UserRole> userRoles) {
        User localUser = userRepository.findByUsername(user.getUsername());
        if (localUser != null){
            System.out.println("User: " + localUser.getUsername() + " already exists");
        } else {
            for (UserRole ur: userRoles) {
                System.out.println(ur.getRole().toString());
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            String encodedPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPass);
            localUser = userRepository.save(user);
        }

        user.setPrimaryAccount(accountService.createPrimaryAccount());
        user.setSavingsAccount(accountService.createSavingAccount());

        return localUser;
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkUserExist(String username, String email) {
        if (findByUsername(username) != null || findByEmail(email) !=null){
            return  true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkUsernameExist(String username) {
        if (findByUsername(username) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkEmailExist(String email) {
        if (findByEmail(email) != null){
            return true;
        }else {
            return false;
        }
    }
}
