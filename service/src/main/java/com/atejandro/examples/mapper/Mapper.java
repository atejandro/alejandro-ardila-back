package com.atejandro.examples.mapper;

import com.atejandro.examples.api.CubeResponse;
import com.atejandro.examples.domain.Coordinate;
import com.atejandro.examples.model.Cube;
import com.atejandro.examples.model.User;

/**
 * Created by atejandro on 11/06/17.
 */
public class Mapper {

    public static Coordinate coord(com.atejandro.examples.api.Coordinate c){
        return new Coordinate(c.getX(), c.getY(), c.getZ());
    }

    public static User user(com.atejandro.examples.api.User u){
        return new User(u.getName(), u.getSurname(), u.getEmail());
    }

    public static com.atejandro.examples.api.User protoUser(User u){
       return com.atejandro.examples.api.User.newBuilder()
                .setEmail(u.getEmail())
                .setName(u.getName())
                .setSurname(u.getSureName()).build();
    }

    public static CubeResponse protoCube(Cube cube){
        return CubeResponse.newBuilder()
                .setCubeId(cube.getId())
                .setCubeSize(cube.getSize())
                .build();
    }

}
