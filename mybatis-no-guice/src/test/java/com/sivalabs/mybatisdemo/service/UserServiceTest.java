package com.sivalabs.mybatisdemo.service;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sivalabs.mybatisdemo.domain.User;
import com.sivalabs.mybatisdemo.service.UserService;

public class UserServiceTest {
    private static UserService userService;

    @BeforeClass
    public static void setup() {
        userService = new UserService();
    }

    @AfterClass
    public static void teardown() {
        userService = null;
    }

    @Test
    public void testGetUserById() {
        User user = insertUser();
        User retrievedUser = userService.getUserById(user.getUserId());
        
        Assert.assertNotNull(retrievedUser);
        System.out.println(retrievedUser);
    }

    @Test
    public void testGetAllUsers() {
        insertUser(); // Ensure at least one user present.

        List<User> users = userService.getAllUsers();
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

        userService.insertUser(user);

        Assert.assertTrue(user.getUserId() != 0);

        return user;
    }

    @Test
    public void testInsertUser() {
        User user = insertUser();

        User createdUser = userService.getUserById(user.getUserId());
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
        User user = userService.getUserById(userId);
        user.setFirstName("TestFirstName" + timestamp);
        user.setLastName("TestLastName" + timestamp);
        userService.updateUser(user);
        User updatedUser = userService.getUserById(userId);
        Assert.assertEquals(user.getFirstName(), updatedUser.getFirstName());
        Assert.assertEquals(user.getLastName(), updatedUser.getLastName());
    }

    @Test
    public void testDeleteUser() {
        int userId = insertUser().getUserId();
        
        User user = userService.getUserById(userId);
        userService.deleteUser(user.getUserId());
        User deletedUser = userService.getUserById(userId);
        Assert.assertNull(deletedUser);
    }
}