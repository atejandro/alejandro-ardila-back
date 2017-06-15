package com.atejandro.examples.service;

import com.atejandro.examples.api.CreateUserRequest;
import com.atejandro.examples.api.UpdateUserRequest;
import com.atejandro.examples.api.UserResponse;
import com.atejandro.examples.api.UserServiceGrpc;
import com.atejandro.examples.exception.UserAlreadyExistsException;
import com.atejandro.examples.exception.UserInvalidArgumentsException;
import com.atejandro.examples.exception.UserNotFoundException;
import com.atejandro.examples.manager.UserManager;
import com.atejandro.examples.mapper.Mapper;
import com.atejandro.examples.model.User;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by atejandro on 14/06/17.
 */
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private UserManager manager;

    @Autowired
    public UserService(UserManager manager) {
        this.manager = manager;
    }

    /**
     */
    public void createUser(CreateUserRequest request,
                           StreamObserver<UserResponse> responseObserver) {
        try {
            User savedUser = manager.save(Mapper.user(request.getUser()));

            responseObserver.onNext(UserResponse.newBuilder()
                    .setUser(Mapper.protoUser(savedUser))
                    .setId(savedUser.getId())
                    .build());

            responseObserver.onCompleted();
        } catch (UserAlreadyExistsException e) {
            responseObserver.onError(Status.ALREADY_EXISTS.asException());
        } catch (UserInvalidArgumentsException e) {
            responseObserver.onError(Status.INVALID_ARGUMENT.asException());
        }
    }

    /**
     */
    public void updateUser(UpdateUserRequest request,
                           StreamObserver<UserResponse> responseObserver) {
        try {
            User updated =
                    manager.update(request.getId(), Mapper.user(request.getUser()));

            responseObserver.onNext(UserResponse.newBuilder()
            .setId(updated.getId())
            .setUser(Mapper.protoUser(updated))
            .build());

            responseObserver.onCompleted();
        } catch (UserNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }
}
