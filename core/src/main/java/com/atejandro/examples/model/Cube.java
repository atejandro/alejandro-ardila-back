package com.atejandro.examples.model;

import com.atejandro.examples.exception.CubeOperationOutOfBoundsException;
import com.atejandro.examples.domain.Coordinate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;

/**
 * Created by atejandro on 9/06/17.
 */
@Entity
@Table(name = "cube")
public class Cube {

    @Id
    @Column(name = "cube_id", unique = true, nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cube_size", nullable = false)
    private int size;

    @Column(name = "cube_content", length = 1000, nullable = false)
    private String cubeContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Transient
    private long[][][] cube;

    @Transient
    private Gson gson = new GsonBuilder().create();

    public Cube(int dim){
        this.size = dim;
        cube = new long[dim+1][dim+1][dim+1];
    }

    public Cube(){
    }

    public long query(Coordinate origin, Coordinate source)throws CubeOperationOutOfBoundsException {
        return subCube(origin, source);
    }

    public void update(Coordinate coord, long value) throws CubeOperationOutOfBoundsException {
        updateValue(coord, value - subCube(coord, coord));
    }

    public void refreshCubeContent(){
        this.cubeContent = gson.toJson(this.cube);
    }

    public void refreshCube(){
        this.cube = gson.fromJson(this.getCubeContent(), long[][][].class);
    }

    private boolean isOutOfBounds(Coordinate coord){
        int x = coord.getX();
        int y = coord.getY();
        int z = coord.getZ();
        return x < 1 || x > size || y < 1 || y > size || z < 1 || z > size;
    }

    private long subCube(Coordinate origin, Coordinate source) throws CubeOperationOutOfBoundsException{
        if(isOutOfBounds(origin) || isOutOfBounds(source)){
            throw new CubeOperationOutOfBoundsException("The range is out of bounds");
        }

        long outerCube = sumSwipe(source.getX(),source.getY(),source.getZ()) -
                sumSwipe(origin.getX()-1,source.getY(),source.getZ()) -
                sumSwipe(source.getX(),origin.getY()-1,source.getZ()) +
                sumSwipe(origin.getX()-1,origin.getY()-1,source.getZ());

        long innerCube = sumSwipe(source.getX(),source.getY(),origin.getZ()-1) -
                sumSwipe(origin.getX()-1,source.getY(),origin.getZ()-1) -
                sumSwipe(source.getX(),origin.getY()-1,origin.getZ()-1) +
                sumSwipe(origin.getX()-1,origin.getY()-1,origin.getZ()-1);

        return outerCube - innerCube;
    }

    private void updateValue(Coordinate coord, long value){
        int z = coord.getZ();
        int x1, y1;
        while(z <= size) {
            x1 = coord.getX();
            while(x1 <= size) {
                y1 = coord.getY();
                while(y1 <= size) {
                    cube[x1][y1][z] += value;
                    y1 += (y1 & -y1 );
                }
                x1 += (x1 & -x1);
            }
            z += (z & -z);
        }
    }

    private long sumSwipe(int x, int y, int z){
        long sum = 0;
        int x1, y1;
        while (z>0) {
            x1=x;
            while(x1>0) {
                y1=y;
                while(y1>0) {
                    sum += cube[x1][y1][z];
                    y1-= (y1 & -y1);
                }
                x1 -= (x1 & -x1);
            }
            z -= (z & -z);

        }
        return sum;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize(){ return this.size; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCubeContent() {
        return cubeContent;
    }

    public void setCubeContent(String cubeContent) {
        this.cubeContent = cubeContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long[][][] getCube() {
        return cube;
    }

    public void setCube(long[][][] cube) {
        this.cube = cube;
    }
}
