package com.atejandro.examples.test

import com.atejandro.examples.domain.Coordinate
import com.atejandro.examples.model.Cube

import scala.collection.mutable.ListBuffer
import scala.util.Random

trait CubeFixture {
  val MaxCubeDimension = 10;
  def randValue: Long = Random.nextInt(500).toLong

  def coord(x: Int, y: Int, z: Int): Coordinate = new Coordinate(x, y, z)

  def newCube(dim: Int = Random.nextInt(MaxCubeDimension)+1) = new Cube(dim)

  def randUpdate(cube: Cube): (Coordinate, Long) = {
    val value = randValue
    val (x, y, z) = (Random.nextInt(cube.getSize)+1,
      Random.nextInt(cube.getSize)+1,
      Random.nextInt(cube.getSize)+1)
    cube.update(coord(x,y,z), value)
    (coord(x, y, z), value)
  }

  def updateInRange(x0:Int,y0:Int,z0:Int,x:Int,y:Int,z:Int)(updates: Int)(implicit cube:Cube): Long ={

    var list = ListBuffer.empty[(Int, Int, Int)]

    def pickFromRange(start: Int, end: Int): Int = {
      var pick: Int = 0
      pick = start + Random.nextInt((end-start) + 1)
      pick
    }

    var sum: Long = 0
    var i: Int = 0
    while( i < updates){
      val xi = pickFromRange(x0, x)
      val yi = pickFromRange(y0, y)
      val zi = pickFromRange(z0, z)
      val value = randValue
      if(list.isEmpty || !list.contains((xi, yi, zi))){
        list.append((xi,yi,zi))
        cube.update(coord(xi, yi, zi), value)
        sum += value
        i += 1
      }
    }
    sum
  }
}
