package com.atejandro.examples.test

import com.atejandro.examples.exception.CubeOperationOutOfBoundsException
import org.scalatest.{BeforeAndAfter, FunSpec, GivenWhenThen, Matchers}

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Created by atejandro on 8/06/17.
  */
class CubeTest extends FunSpec with BeforeAndAfter with GivenWhenThen with Matchers {

  describe("Cube summation algorithm"){
    it("should update a value in the cube"){
      new CubeFixture {
        Given("a cube of any size")
        val cube = newCube()
        When("a single value is updated")
        val (coord , value) = randUpdate(cube)
        Then("its query should be the value")
        cube.query(coord, coord) shouldBe value
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
        cube.query(coord(2, 2, 2), coord(7, 7, 7)) shouldBe sum
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
        cube.query(coord(1, 1, 1), coord(1, 1, 1)) shouldBe 0
        cube.query(coord(7,7,7) , coord(10,10,10)) shouldBe 0
      }
    }

    it("should throw an exception when trying to update a value out of range"){
      new CubeFixture {
        Given("a cube of size 2")
        implicit val cube = newCube(2)
        When("Non existent bounds are updated")
        Then("An exception should be thrown")
        val exception1 = intercept[CubeOperationOutOfBoundsException]{
          cube.update(coord(3, 3, 3), 10)
        }
        val exception2 = intercept[CubeOperationOutOfBoundsException]{
          cube.update(coord(0, 0, 0), 10)
        }
        val exception3 = intercept[CubeOperationOutOfBoundsException]{
          cube.update(coord(-1, -1, 1), 10)
        }
        assert(exception1 != null)
        assert(exception2 != null)
        assert(exception3 != null)
      }
    }
  }

}
