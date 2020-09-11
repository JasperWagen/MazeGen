package services

import java.awt.image.BufferedImage

import org.scalatest.funsuite.AnyFunSuite
import imageCreator.ArrayToImage

import scala.collection.mutable.ListBuffer

class imageCreatorSpec extends AnyFunSuite{
    val arrayToImgTest = new ArrayToImage
    test("Create blank buffered image"){
        val testArray = Array.ofDim[Int](100,100)
        assert(arrayToImgTest.createImg(testArray, 13158600, 20).isInstanceOf[BufferedImage])
    }

    test("Test image height dimension"){
        val testArray = Array.ofDim[Int](100,150)
        assert(arrayToImgTest.createImg(testArray, 13158600, 20).getHeight == testArray.length*20)
    }

    test("Test image width dimension"){
        val testArray = Array.ofDim[Int](100,150)
        assert(arrayToImgTest.createImg(testArray, 13158600, 20).getWidth == testArray(0).length*20)
    }
}