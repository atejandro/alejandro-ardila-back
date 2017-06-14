package com.atejandro.examples.service;

import com.atejandro.examples.api.*;
import com.atejandro.examples.exception.CubeOperationOutOfBoundsException;
import com.atejandro.examples.manager.CubeManager;
import com.atejandro.examples.mapper.Mapper;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        responseObserver.onNext(CubeResponse.newBuilder()
                .setCubeId(1L)
                .setCubeSize(request.getCubeSize())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateCube(UpdateCubeRequest request, StreamObserver<UpdateCubeReponse> responseObserver){

        Coordinate coordToUpdate = request.getCoord();
        try{
            manager.update(Mapper.coord(coordToUpdate), request.getValue());
            responseObserver.onNext(UpdateCubeReponse.newBuilder()
                    .setResponse(UpdateCubeReponse.Response.OK)
                    .build());
            responseObserver.onCompleted();
        } catch (CubeOperationOutOfBoundsException e){
            responseObserver.onError(Status.INVALID_ARGUMENT.asException());
        }
    }

    @Override
    public void queryCube(QueryCubeRequest request, StreamObserver<QueryCubeResponse> responseObserver){


        try{
            long answer = manager.query(Mapper.coord(request.getTo()), Mapper.coord(request.getFrom()));
            responseObserver.onNext(QueryCubeResponse.newBuilder().setAnswer(answer).build());
            responseObserver.onCompleted();
        } catch (CubeOperationOutOfBoundsException e){
            responseObserver.onError(Status.INVALID_ARGUMENT.asException());
        }
    }

}
