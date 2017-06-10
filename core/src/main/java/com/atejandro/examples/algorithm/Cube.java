package com.atejandro.examples.algorithm;

/**
 * Created by atejandro on 9/06/17.
 */
public class Cube {

    private long[][][] cube;

    private int size;

    public Cube(int dim){
        this.size = dim;
        cube = new long[dim+1][dim+1][dim+1];
    }

    public int getSize(){ return this.size; }

    public long query(int x0, int y0, int z0, int x, int y, int z)throws CubeOperationOutOfBoundsException {
        return subCube(x0, y0, z0, x, y, z);
    }

    public void update(int x, int y, int z, long value) throws CubeOperationOutOfBoundsException {
        updateValue(x,y,z, value - subCube(x,y,z,x,y,z));
    }

    public boolean isOutOfBounds(int x, int y, int z){
        return x < 1 || x > size || y < 1 || y > size || z < 1 || z > size;
    }

    private long subCube(int x0, int y0, int z0, int x, int y, int z) throws CubeOperationOutOfBoundsException{
        if(isOutOfBounds(x0, y0, z0) || isOutOfBounds(x, y, z)){
            throw new CubeOperationOutOfBoundsException("");
        }

        long outerCube = sumSwipe(x,y,z)- sumSwipe(x0-1,y,z)
                - sumSwipe(x,y0-1,z) + sumSwipe(x0-1,y0-1,z);

        long innerCube = sumSwipe(x,y,z0-1) - sumSwipe(x0-1,y,z0-1)
                - sumSwipe(x,y0-1,z0-1)  + sumSwipe(x0-1,y0-1,z0-1);

        return outerCube - innerCube;
    }

    private void updateValue(int x, int y, int z, long value){
        int x1, y1;
        while(z <= size) {
            x1 = x;
            while(x1 <= size) {
                y1 = y;
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
}
