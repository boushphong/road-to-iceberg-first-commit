package com.github.boushphong

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.collection.JavaConverters._

class SparkSessionBuilder(val properties_path: String) {
  def build(): SparkSession = {
    val properties = new Properties()
    properties.load(getClass.getResourceAsStream(properties_path))

    val sparkConf = new SparkConf()
    properties.asScala.toMap.foreach { case (key, value) =>
      sparkConf.set(key, value)
    }

    SparkSession.builder.config(sparkConf).getOrCreate()
  }
}
