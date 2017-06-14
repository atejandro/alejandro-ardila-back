package com.atejandro.examples;

import com.atejandro.examples.config.IntegrationTestConfig;
import com.atejandro.examples.context.PersistenceContext;
import com.atejandro.examples.exception.UserAlreadyExistsException;
import com.atejandro.examples.exception.UserInvalidArgumentsException;
import com.atejandro.examples.exception.UserNotFoundException;
import com.atejandro.examples.manager.UserManager;
import com.atejandro.examples.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by atejandro on 13/06/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationTestConfig.class, PersistenceContext.class})
public class UserManagerIT {

    @Autowired
    UserManager manager;

    @Test
    public void createUserTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException {
        User user = new User("user1", "surname1", "user@email.com");
        User savedUser = manager.save(user);
        assertTrue(savedUser.getId() != null);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void createUserAlreadyExistsTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException {
        User user = new User("user2", "surname2", "user2@email.com");
        manager.save(user);
        manager.save(user);
    }

    @Test(expected = UserInvalidArgumentsException.class)
    public void createUserWithInvalidArgumentsTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException {
        User user = new User(null, null, null);
        manager.save(user);
    }

    @Test
    public void deleteUserTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException, UserNotFoundException {
        User user = new User("userDelete", "surnameDelete", "user@delete.com");
        Long userToDeleteId = manager.save(user).getId();
        manager.delete(userToDeleteId);
        assertFalse(manager.find(userToDeleteId).isPresent());
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteNonExistentUserTest() throws UserNotFoundException {
        Long id = Long.MAX_VALUE;
        manager.delete(id);
    }

    @Test
    public void updateUserTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException, UserNotFoundException {
        User userToUpdate = manager.save(new User("userUpdate", "surnameUpdate", "user@update.com"));
        String newName = "a new name";
        userToUpdate.setName(newName);
        User updated = manager.update(userToUpdate.getId(), userToUpdate);
        assertTrue(updated.getId() == userToUpdate.getId());
        assertTrue(updated.getName().equals(newName));
    }

    @Test(expected = UserNotFoundException.class)
    public void updateNonExistentUserTest() throws UserNotFoundException {
        User userToUpdate = new User("userUpdate", "surnameUpdate", "user@update.com");
        String newName = "a new name";
        userToUpdate.setName(newName);
        manager.update(Long.MAX_VALUE, userToUpdate);
    }
}
