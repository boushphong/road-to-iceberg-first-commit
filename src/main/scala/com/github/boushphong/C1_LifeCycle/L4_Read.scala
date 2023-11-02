package com.github.boushphong.C1_LifeCycle

import com.github.boushphong.SparkSessionBuilder

object L4_Read extends App {
  val spark = new SparkSessionBuilder("/jdbc_catalog.properties").build();

  val snapshotDF = spark.sql("select * from people.history")

  snapshotDF.show(false)

  val made_current_at_v1 = snapshotDF.select("made_current_at").collect()(0)(0).toString
  val made_current_at_v2 = snapshotDF.select("made_current_at").collect()(1)(0).toString

  spark.sql(s"select * from people TIMESTAMP AS OF '$made_current_at_v1'").show(false)
  spark.sql(s"select * from people TIMESTAMP AS OF '$made_current_at_v2'").show(false)

  val snapshot_id_v1 = snapshotDF.select("snapshot_id").collect()(0)(0).toString
  val snapshot_id_v2 = snapshotDF.select("snapshot_id").collect()(1)(0).toString

  spark.sql(s"select * from people VERSION AS OF '$snapshot_id_v1'").show(false)
  spark.sql(s"select * from people VERSION AS OF '$snapshot_id_v2'").show(false)
}
