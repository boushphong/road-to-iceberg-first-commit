package com.github.boushphong

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

import java.util.Properties
import scala.collection.JavaConverters._

class SparkSessionBuilder(val properties_path: String) {
  System.setProperty("aws.accessKeyId", "admin")
  System.setProperty("aws.secretAccessKey", "password")
  System.setProperty("aws.region", "us-east-1")

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
