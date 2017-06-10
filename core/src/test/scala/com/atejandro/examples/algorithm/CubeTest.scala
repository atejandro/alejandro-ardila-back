package com.atejandro.examples.algorithm

import org.scalatest.{BeforeAndAfter, FunSpec, GivenWhenThen, Matchers}

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Created by atejandro on 8/06/17.
  */
class CubeTest extends FunSpec with BeforeAndAfter with GivenWhenThen with Matchers {

  sealed trait CubeFixture {
    val MaxCubeDimension = 10;
    def randValue: Long = Random.nextInt(500).toLong

    def newCube(dim: Int = Random.nextInt(MaxCubeDimension)+1) = new Cube(dim)

    def randUpdate(cube: Cube): (Int, Int, Int, Long) = {
      val value = randValue
      val (x, y, z) = (Random.nextInt(cube.getSize)+1,
                      Random.nextInt(cube.getSize)+1,
                      Random.nextInt(cube.getSize)+1)
      cube.update(x,y,z, value)
      (x, y, z, value)
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
          cube.update(xi, yi, zi, value)
          sum += value
          i += 1
        }
      }
      sum
    }
  }

  describe("Cube summation algorithm"){
    it("should update a value in the cube"){
      new CubeFixture {
        Given("a cube of any size")
        val cube = newCube()
        When("a single value is updated")
        val (x, y, z, value) = randUpdate(cube)
        Then("its query should be the value")
        cube.query(x, y, z, x, y, z) shouldBe value
      }
    }

    it("should add up cube values from a range") {
      new CubeFixture {
        Given("a cube of size 10")
        implicit val cube = newCube(10)
        When("some values are updated")
        val sum = updateInRange(2, 2, 2, 7, 7, 7)(updates = 4)
        And("a query is made in a range covering the updated values")
        Then("the result should be the sum of the values")
        cube.query(2, 2, 2, 7, 7, 7) shouldBe sum
      }
    }

    it("non updated values from outside range should be zero"){
      new CubeFixture {
        Given("a cube of size 10")
        implicit val cube = newCube(10)
        When("some values are updated")
        val sum = updateInRange(2, 2, 2, 7, 7, 7)(updates = 4)
        And("a query is made in a range covering the updated values")
        Then("the result should be the sum of the values")
        cube.query(1, 1, 1, 1, 1, 1) shouldBe 0
        cube.query(7,7,7,10,10,10) shouldBe 0
      }
    }

    it("should throw an exception when trying to update a value out of range"){
      new CubeFixture {
        Given("a cube of size 2")
        implicit val cube = newCube(2)
        When("Non existent bounds are updated")
        Then("An exception should be thrown")
        val exception1 = intercept[CubeOperationOutOfBoundsException]{
          cube.update(3, 3, 3, 10)
        }
        val exception2 = intercept[CubeOperationOutOfBoundsException]{
          cube.update(0, 0, 0, 10)
        }
        val exception3 = intercept[CubeOperationOutOfBoundsException]{
          cube.update(-1, -1, 1, 10)
        }
        assert(exception1 != null)
        assert(exception2 != null)
        assert(exception3 != null)
      }
    }
  }

}
