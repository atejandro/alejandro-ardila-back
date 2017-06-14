package com.atejandro.examples.fixture;

import com.atejandro.examples.domain.Coordinate;
import com.atejandro.examples.exception.UserAlreadyExistsException;
import com.atejandro.examples.exception.UserInvalidArgumentsException;
import com.atejandro.examples.manager.UserManager;
import com.atejandro.examples.model.User;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by atejandro on 13/06/17.
 */
@Component
public class CubeFixture {


    private UserManager userManager;

    private User defaultUser;

    @Autowired
    public CubeFixture(UserManager manager) throws UserAlreadyExistsException, UserInvalidArgumentsException {
        this.userManager = manager;
        this.defaultUser = userManager.save(new User("userCube", "surnameCube", "user@cube.com"));
    }

    public Coordinate coord(int x, int y, int z){
        return new Coordinate(x, y, z);
    }

    public User user(){
        return defaultUser;
    }

    public Coordinate origin(){
        return coord(1,1,1);
    }

    public Coordinate source(int size){
        return coord(size, size, size);
    }




}
