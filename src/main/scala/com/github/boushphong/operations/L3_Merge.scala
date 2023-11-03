package com.github.boushphong.operations

import com.github.boushphong.SparkSessionBuilder

import java.sql.Timestamp

object L3_Merge extends App {
  val spark = new SparkSessionBuilder("/jdbc_catalog.properties").build()

  import spark.implicits._
  val data = Seq(
    (2, "Leo", 29, Timestamp.valueOf("2023-03-03 09:09:23")),
    (3, "Deli", 30, Timestamp.valueOf("2023-03-10 20:11:00")),
    (4, "Dora", 31, Timestamp.valueOf("2023-03-11 11:12:30")),
  )

  private val df = data.toDF("id", "name", "age", "registered_at")
  df.printSchema()

  df.createOrReplaceTempView("tmp_people")

  spark.sql(
    """
      |MERGE INTO people
      |USING tmp_people
      |ON people.id = tmp_people.id
      |WHEN MATCHED THEN UPDATE SET *
      |WHEN NOT MATCHED THEN INSERT *
      |""".stripMargin
  )

  spark.sql("SELECT * FROM people").show(false)
}
