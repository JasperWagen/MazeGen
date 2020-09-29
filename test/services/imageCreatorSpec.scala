package services
import models.mazeInfo.MazeRequestData
import java.awt.image.BufferedImage

import org.scalatest.funsuite.AnyFunSuite
import imageCreator.ArrayToImage

import scala.collection.mutable.ListBuffer

class imageCreatorSpec extends AnyFunSuite{
    val arrayToImgTest = new ArrayToImage
    val testRequestData = MazeRequestData()
    test("Create blank buffered image"){
        val testArray = Array.ofDim[Byte](100,100)
        assert(arrayToImgTest.imgInit(testArray, testRequestData).isInstanceOf[BufferedImage])
    }

    test("Test image height dimension"){
        val testArray = Array.ofDim[Byte](100,150)
        assert(arrayToImgTest.imgInit(testArray, testRequestData).getHeight == testArray.length*20)
    }

    test("Test image width dimension"){
        val testArray = Array.ofDim[Byte](100,150)
        assert(arrayToImgTest.imgInit(testArray, testRequestData).getWidth == testArray(0).length*20)
    }
}