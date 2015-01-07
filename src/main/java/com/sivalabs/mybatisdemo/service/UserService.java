package com.sivalabs.mybatisdemo.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.ibatis.session.SqlSession;

import com.sivalabs.mybatisdemo.domain.User;
import com.sivalabs.mybatisdemo.mappers.UserMapper;

public class UserService {
    public void insertUser(User user) {
        writeTransactional(UserMapper.class, mapper -> mapper.insertUser(user));
    }

    public User getUserById(Integer userId) {
        return readTransaction(UserMapper.class, mapper -> mapper.getUserById(userId));
    }

    public List<User> getAllUsers() {
        return readTransaction(UserMapper.class, mapper -> mapper.getAllUsers());
    }

    public void updateUser(User user) {
        writeTransactional(UserMapper.class, mapper -> mapper.updateUser(user));
    }

    public void deleteUser(Integer userId) {
        writeTransactional(UserMapper.class, mapper -> mapper.deleteUser(userId));
    }
    
    private <M> void writeTransactional(Class<M> clazz, Consumer<M> consumer) {
        this.<Void>doTransaction(sqlSession -> {
            consumer.accept(sqlSession.getMapper(clazz));
            sqlSession.commit();
            return null;
        });
    }
    
    private <R, M> R readTransaction(Class<M> clazz, Function<M, R> action) {
        return doTransaction(sqlSession -> action.apply(sqlSession.getMapper(clazz)));
    }

    private <R> R doTransaction(Function<SqlSession, R> action) {
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
        
        try {
            return action.apply(sqlSession);
        } finally {
            sqlSession.close();
        }
    }
}
