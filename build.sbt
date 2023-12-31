ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "road-to-iceberg-first-commit"
  )

val sparkVersion = "3.5.0"
val icebergVersion = "1.4.2"
val jacksonVersion = "2.15.1"


libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.iceberg" % "iceberg-core" % icebergVersion,
  "org.apache.iceberg" %% "iceberg-spark-3.5" % icebergVersion,
  "org.apache.iceberg" %% "iceberg-spark-runtime-3.5" % icebergVersion,
  "org.apache.iceberg" % "iceberg-aws" % icebergVersion,
  "org.apache.iceberg" % "iceberg-aws-bundle" % icebergVersion,
  "org.postgresql" % "postgresql" % "42.5.4",
)

val `jackson-core` = "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion
val `jackson-databind` = "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion


dependencyOverrides += `jackson-core`
dependencyOverrides += `jackson-databind`

libraryDependencies ++= Seq(
  `jackson-databind`,
  `jackson-core`
)
