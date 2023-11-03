package com.github.boushphong.operations

import com.github.boushphong.SparkSessionBuilder

object L4_Delete extends App {
  val spark = new SparkSessionBuilder("/jdbc_catalog.properties").build()

  spark.sql(
    """
      |DELETE FROM people WHERE id = 3;
      |""".stripMargin
  )

  spark.sql("SELECT * FROM people").show(false)
}
