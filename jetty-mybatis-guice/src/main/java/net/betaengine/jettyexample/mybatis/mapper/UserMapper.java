package net.betaengine.jettyexample.mybatis.mapper;

import net.betaengine.jettyexample.mybatis.domain.User;

public interface UserMapper {
    User getUser(String userId);
    
    void brokenAdd(User user);
}
