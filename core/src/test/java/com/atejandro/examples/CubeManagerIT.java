package com.atejandro.examples;

import com.atejandro.examples.config.IntegrationTestConfig;
import com.atejandro.examples.context.PersistenceContext;
import com.atejandro.examples.domain.Coordinate;
import com.atejandro.examples.exception.CubeNotFoundException;
import com.atejandro.examples.exception.UserAlreadyExistsException;
import com.atejandro.examples.exception.UserInvalidArgumentsException;
import com.atejandro.examples.exception.UserNotFoundException;
import com.atejandro.examples.fixture.CubeFixture;
import com.atejandro.examples.manager.CubeManager;
import com.atejandro.examples.model.Cube;
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
public class CubeManagerIT {

    @Autowired
    CubeManager cubeManager;

    @Autowired
    CubeFixture f;

    private static final int CUBE_SIZE = 4;

    @Test
    public void createCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException, UserNotFoundException {
        Cube cube = cubeManager.save(f.user().getId(), CUBE_SIZE);
        assertTrue(cube.getUser().getId() == f.user().getId());
        assertTrue(cube.getSize() == CUBE_SIZE);
        assertTrue(cube.query(f.coord(1,1,1), f.coord(4,4,4)) == 0);
    }

    @Test(expected = UserNotFoundException.class)
    public void createCubeWithNonExistentUserTest() throws UserNotFoundException {
        cubeManager.save(Long.MAX_VALUE, CUBE_SIZE);
    }

    @Test
    public void deleteCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException,
            UserNotFoundException, CubeNotFoundException {
        Cube cube = cubeManager.save(f.user().getId(), CUBE_SIZE);
        cubeManager.delete(cube.getId());
        assertFalse(cubeManager.find(cube.getId()).isPresent());
    }

    @Test(expected = CubeNotFoundException.class)
    public void deleteCubeWithNonExistentCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException,
            UserNotFoundException, CubeNotFoundException {
        cubeManager.delete(Long.MAX_VALUE);
    }

    @Test
    public void updateCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException,
            UserNotFoundException, CubeNotFoundException {
        Cube cube = cubeManager.save(f.user().getId(), CUBE_SIZE);
        long value = 5;
        Coordinate coord = f.coord(2,2,2);
        Cube updatedCube = cubeManager.update(cube.getId(), coord, value);
        assertTrue(updatedCube.query(coord, coord) == value);
    }

    @Test(expected = CubeNotFoundException.class)
    public void updateNonExistentCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException,
            UserNotFoundException, CubeNotFoundException {
        long value = 5;
        Coordinate coord = f.coord(2,2,2);
        cubeManager.update(Long.MAX_VALUE, coord, value);
    }

    @Test
    public void queryCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException, UserNotFoundException, CubeNotFoundException {
        Cube cube = cubeManager.save(f.user().getId(), CUBE_SIZE);
        assertTrue(cubeManager.query(cube.getId(), f.origin(), f.source(CUBE_SIZE)) == 0);
    }

    @Test(expected = CubeNotFoundException.class)
    public void queryNonExistentCubeTest()
            throws UserAlreadyExistsException, UserInvalidArgumentsException, UserNotFoundException, CubeNotFoundException {
        cubeManager.query(Long.MAX_VALUE, f.origin(), f.source(CUBE_SIZE));
    }
}
