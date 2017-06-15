package com.atejandro.examples.service;

import com.atejandro.examples.api.*;
import com.atejandro.examples.exception.CubeNotFoundException;
import com.atejandro.examples.exception.CubeOperationOutOfBoundsException;
import com.atejandro.examples.exception.UserNotFoundException;
import com.atejandro.examples.manager.CubeManager;
import com.atejandro.examples.mapper.Mapper;
import com.atejandro.examples.model.Cube;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by atejandro on 11/06/17.
 */
@Component
public class CubeSummationService extends CubeSummationServiceGrpc.CubeSummationServiceImplBase {

    CubeManager manager;

    @Autowired
    public CubeSummationService(CubeManager manager){
        this.manager = manager;
    }

    @Override
    public void createCube(CreateCubeRequest request, StreamObserver<CubeResponse> responseObserver){
        try {
            Cube createdCube = manager.save(request.getUserId(), request.getCubeSize());
            responseObserver.onNext(Mapper.protoCube(createdCube));
            responseObserver.onCompleted();
        } catch (UserNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void updateCube(UpdateCubeRequest request, StreamObserver<UpdateCubeReponse> responseObserver){
        Coordinate coordToUpdate = request.getCoord();
        try{
            manager.update(request.getCubeId(), Mapper.coord(coordToUpdate), request.getValue());
            responseObserver.onNext(UpdateCubeReponse.newBuilder()
                    .setResponse(UpdateCubeReponse.Response.OK)
                    .build());
            responseObserver.onCompleted();
        } catch (CubeOperationOutOfBoundsException e){
            responseObserver.onError(Status.OUT_OF_RANGE.asException());
        } catch (CubeNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void listCube(ListCubeRequest request, StreamObserver<ListCubeResponse> responseObserver){
        try {
            List<CubeResponse> cubes = manager.list(request.getUserId())
                                        .stream()
                                        .map(Mapper::protoCube)
                                        .collect(Collectors.toList());

            responseObserver.onNext(ListCubeResponse
                    .newBuilder()
                    .addAllCubes(cubes)
                    .build());

            responseObserver.onCompleted();
        } catch (UserNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

    @Override
    public void queryCube(QueryCubeRequest request, StreamObserver<QueryCubeResponse> responseObserver){
        try{

            long answer =
                    manager.query(request.getCubeId(),Mapper.coord(request.getTo()), Mapper.coord(request.getFrom()));

            responseObserver.onNext(QueryCubeResponse.newBuilder()
                    .setCubeId(request.getCubeId())
                    .setFrom(request.getFrom())
                    .setTo(request.getTo())
                    .setAnswer(answer)
                    .build());

            responseObserver.onCompleted();
        } catch (CubeOperationOutOfBoundsException e){
            responseObserver.onError(Status.INVALID_ARGUMENT.asException());
        } catch (CubeNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND.asException());
        }
    }

}
