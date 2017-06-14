package com.atejandro.examples.mapper;

import com.atejandro.examples.domain.Coordinate;

/**
 * Created by atejandro on 11/06/17.
 */
public class Mapper {

    public static Coordinate coord(com.atejandro.examples.api.Coordinate c){
        return new Coordinate(c.getX(), c.getY(), c.getZ());
    }

}
