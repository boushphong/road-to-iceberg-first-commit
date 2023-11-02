package com.github.boushphong.C1_LifeCycle

import com.github.boushphong.SparkSessionBuilder

object L2_Insert extends App {
  val spark = new SparkSessionBuilder("/jdbc_catalog.properties").build();

  spark.sql(
    """
      |INSERT INTO people VALUES
      |(1, 'Phong', 26, CAST('2023-03-07T08:10:23' AS TIMESTAMP)),
      |(2, 'Leo', 29, CAST('2023-03-01T01:10:23' AS TIMESTAMP));
      |""".stripMargin
  )
}
