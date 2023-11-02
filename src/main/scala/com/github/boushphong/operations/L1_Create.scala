package com.github.boushphong.operations

import com.github.boushphong.SparkSessionBuilder

object L1_Create extends App {
  val spark = new SparkSessionBuilder("/jdbc_catalog.properties").build();

  spark.sql(
    """
      |CREATE TABLE people (
      |   id INT,
      |   name STRING,
      |   age INT,
      |   registered_at TIMESTAMP
      |)
      |USING iceberg
      |PARTITIONED BY (DAY(registered_at));
      |""".stripMargin
  )
}
