package com.sivalabs.mybatisdemo;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sivalabs.mybatisdemo.dao.UserDao;
import com.sivalabs.mybatisdemo.model.User;

public class UserServiceTest {
    private static UserDao userDao;

    @BeforeClass
    public static void setup() {
        userDao = new UserDao();
    }

    @AfterClass
    public static void teardown() {
        userDao = null;
    }

    @Test
    public void testGetUserById() {
        User user = insertUser();
        User retrievedUser = userDao.getUserById(user.getUserId());
        
        Assert.assertNotNull(retrievedUser);
        System.out.println(retrievedUser);
    }

    @Test
    public void testGetAllUsers() {
        insertUser(); // Ensure at least one user present.

        List<User> users = userDao.getAllUsers();
        Assert.assertNotNull(users);
        for (User user : users)
        {
            System.out.println(user);
        }

    }

    private User insertUser() {
        User user = new User();
        user.setEmailId(UUID.randomUUID() + "@x.com");
        user.setPassword("secret");
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");

        userDao.insertUser(user);

        Assert.assertTrue(user.getUserId() != 0);

        return user;
    }

    @Test
    public void testInsertUser() {
        User user = insertUser();

        User createdUser = userDao.getUserById(user.getUserId());
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(user.getEmailId(), createdUser.getEmailId());
        Assert.assertEquals(user.getPassword(), createdUser.getPassword());
        Assert.assertEquals(user.getFirstName(), createdUser.getFirstName());
        Assert.assertEquals(user.getLastName(), createdUser.getLastName());

    }

    @Test
    public void testUpdateUser() {
        int userId = insertUser().getUserId();
        
        long timestamp = System.currentTimeMillis();
        User user = userDao.getUserById(userId);
        user.setFirstName("TestFirstName" + timestamp);
        user.setLastName("TestLastName" + timestamp);
        userDao.updateUser(user);
        User updatedUser = userDao.getUserById(userId);
        Assert.assertEquals(user.getFirstName(), updatedUser.getFirstName());
        Assert.assertEquals(user.getLastName(), updatedUser.getLastName());
    }

    @Test
    public void testDeleteUser() {
        int userId = insertUser().getUserId();
        
        User user = userDao.getUserById(userId);
        userDao.deleteUser(user.getUserId());
        User deletedUser = userDao.getUserById(userId);
        Assert.assertNull(deletedUser);
    }
}