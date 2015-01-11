package net.betaengine.jettyexample.mybatis.service;

import net.betaengine.jettyexample.mybatis.domain.User;
import net.betaengine.jettyexample.mybatis.mapper.UserMapper;
import org.mybatis.guice.transactional.Isolation;
import org.mybatis.guice.transactional.Transactional;

import javax.inject.Inject;

// TODO: what happens if you don't add @Transactional to a method that uses userMapper?
@Transactional(isolation = Isolation.SERIALIZABLE)
public class FooService {
    private final UserMapper userMapper;
    
    @Inject // See https://github.com/google/guice/wiki/KeepConstructorsHidden
    /* default */ FooService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User doSomeBusinessStuff(String userId) {
        return userMapper.getUser(userId);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void brokenInsert(User user) {
        userMapper.brokenAdd(user);
    }

    public void brokenInsert2(User user) {
        userMapper.brokenAdd(user);
    }
}
