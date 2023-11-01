import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object IcebergSparkJob extends App {
  // Create a SparkConf object and set all the configurations
  private val sparkConf = new SparkConf()
    .setAppName("Iceberg Spark Job")
    .setMaster("local")
    .set("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
    .set("spark.sql.catalog.local", "org.apache.iceberg.spark.SparkCatalog")
    .set("spark.sql.catalog.local.catalog-impl", "org.apache.iceberg.jdbc.JdbcCatalog")
    .set("spark.sql.catalog.local.uri", "jdbc:postgresql://localhost:5432/postgres")
    .set("spark.sql.catalog.local.jdbc.user", "postgres")
    .set("spark.sql.catalog.local.jdbc.password", "postgres")
    .set("spark.sql.catalog.my_catalog.jdbc.verifyServerCertificate", "true")
    .set("spark.sql.catalog.my_catalog.jdbc.useSSL", "true")
    .set("spark.sql.catalog.local.warehouse", "./spark-warehouse")
    .set("spark.sql.defaultCatalog", "local")
    .set("spark.sql.catalogImplementation", "in-memory")

  // Create a SparkSession using the SparkConf
  private val spark = SparkSession.builder.config(sparkConf).getOrCreate()

  spark.sql("DROP TABLE IF EXISTS warehouse.dataset")

  spark.sql("CREATE DATABASE IF NOT EXISTS dataset")

  private val df = spark.read.parquet("./data/")

  df.show(10,truncate = false)

  df.write.saveAsTable("warehouse.dataset")
}