package com.atejandro.examples.manager;

import com.atejandro.examples.exception.CubeNotFoundException;
import com.atejandro.examples.exception.CubeOperationOutOfBoundsException;
import com.atejandro.examples.exception.UserNotFoundException;
import com.atejandro.examples.model.Cube;
import com.atejandro.examples.domain.Coordinate;
import com.atejandro.examples.model.User;
import com.atejandro.examples.repository.CubeRepository;
import com.atejandro.examples.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by atejandro on 11/06/17.
 */
@Service
public class CubeManager {

    private CubeRepository cubeRepository;

    private UserRepository userRepository;

    private static final String CUBE_NOT_FOUND = "the cube you are looking for doesn't exist";


    @Autowired
    public CubeManager(CubeRepository repo, UserRepository userRepository){
        this.cubeRepository = repo;
        this.userRepository = userRepository;
    }

    public Cube save(Long userId, int cubeSize) throws UserNotFoundException {
        Optional<User> userOption = userRepository.findById(userId);

        if(userOption.isPresent()){
            User user = userOption.get();
            Cube cube = new Cube(cubeSize);
            cube.refreshCubeContent();
            cube.setUser(user);
            cubeRepository.save(cube);
            return cube;
        } else {
            throw new UserNotFoundException("the user does not exist");
        }
    }

    public Optional<Cube> find(Long cubeId){
        return cubeRepository.findById(cubeId);
    }

    public void delete(Long cubeId) throws CubeNotFoundException {
        Optional<Cube> cubeOption = cubeRepository.findById(cubeId);

        if(cubeOption.isPresent()){
            cubeRepository.delete(cubeOption.get());
        } else {
            throw new CubeNotFoundException(CUBE_NOT_FOUND);
        }
    }

    public Cube update(Long cubeId, Coordinate coordinate, long value)
            throws CubeOperationOutOfBoundsException, CubeNotFoundException {
        Optional<Cube> cubeOption = cubeRepository.findById(cubeId);

        if(cubeOption.isPresent()){
            Cube cube = cubeOption.get();
            cube.refreshCube();
            cube.update(coordinate, value);
            cubeRepository.update(cube);
            return cube;
        } else {
            throw new CubeNotFoundException(CUBE_NOT_FOUND);
        }
    }

    public long query(Long id, Coordinate origin, Coordinate source)
            throws CubeOperationOutOfBoundsException, CubeNotFoundException {
        Optional<Cube> cubeOption = cubeRepository.findById(id);

        if(cubeOption.isPresent()){
            Cube cube = cubeOption.get();
            cube.refreshCube();
            return cube.query(origin, source);
        } else {
            throw new CubeNotFoundException(CUBE_NOT_FOUND);
        }
    }

}
