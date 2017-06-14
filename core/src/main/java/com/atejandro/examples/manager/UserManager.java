package com.atejandro.examples.manager;

import com.atejandro.examples.exception.UserAlreadyExistsException;
import com.atejandro.examples.exception.UserInvalidArgumentsException;
import com.atejandro.examples.exception.UserNotFoundException;
import com.atejandro.examples.model.User;
import com.atejandro.examples.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by atejandro on 13/06/17.
 */
@Service
public class UserManager {

    private UserRepository repo;

    @Autowired
    public UserManager(UserRepository repo) {
        this.repo = repo;
    }

    public User save(User user) throws UserAlreadyExistsException, UserInvalidArgumentsException {
        if(!user.hasCompulsoryFields()){
            throw new UserInvalidArgumentsException("User has invalid arguments");
        }

        Optional<User> existentUser = repo.findByNaturalId(user.getEmail());

        if(!existentUser.isPresent()){
           repo.save(user);
           return user;
        } else {
            throw new UserAlreadyExistsException("the user already exists.");
        }
    }

    public Optional<User> find(Long id) {
        return repo.findById(id);
    }

    public void delete(Long userId) throws UserNotFoundException {
        Optional<User> userToDelete = repo.findById(userId);

        if(userToDelete.isPresent()){
            repo.delete(userToDelete.get());
        } else {
            throw new UserNotFoundException("the user to delete was not found");
        }
    }

    public User update(Long id, User user) throws UserNotFoundException {
        Optional<User> userToUpdate = repo.findById(id);

        if(userToUpdate.isPresent()){
            User update = userToUpdate.get();
            update.setEmail(user.getEmail());
            update.setName(user.getName());
            update.setSureName(user.getSureName());
            repo.update(update);
            return update;
        } else {
            throw new UserNotFoundException("the user to delete was not found");
        }
    }


}
