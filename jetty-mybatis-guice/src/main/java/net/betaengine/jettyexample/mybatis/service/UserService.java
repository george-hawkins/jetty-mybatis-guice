package net.betaengine.jettyexample.mybatis.service;

import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import net.betaengine.jettyexample.mybatis.domain.User;
import net.betaengine.jettyexample.mybatis.mapper.UserMapper;

import com.google.inject.Inject;

public class UserService {
    private final UserMapper userMapper;
    
    @Inject
    UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    @Transactional
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Transactional
    public User getUserById(Integer userId) {
        return userMapper.getUserById(userId);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    @Transactional
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Transactional
    public void deleteUser(Integer userId) {
        userMapper.deleteUser(userId);
    }
}
