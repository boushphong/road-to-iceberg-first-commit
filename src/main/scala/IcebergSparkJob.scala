import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object IcebergSparkJob extends App {
  private val sparkConf = new SparkConf()
    .setAppName("Iceberg Spark Job")
    .setMaster("local")
    .set("spark.sql.defaultCatalog", "iceberg_catalog")
    .set("spark.sql.catalogImplementation", "in-memory")
    .set("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
    .set("spark.sql.catalog.iceberg_catalog", "org.apache.iceberg.spark.SparkCatalog")
    .set("spark.sql.catalog.iceberg_catalog.catalog-impl", "org.apache.iceberg.jdbc.JdbcCatalog")
    .set("spark.sql.catalog.iceberg_catalog.uri", "jdbc:postgresql://127.0.0.1:5432/postgres")
    .set("spark.sql.catalog.iceberg_catalog.jdbc.user", "postgres")
    .set("spark.sql.catalog.iceberg_catalog.jdbc.password", "postgres")
    .set("spark.sql.catalog.iceberg_catalog.jdbc.verifyServerCertificate", "true")
    .set("spark.sql.catalog.iceberg_catalog.jdbc.useSSL", "true")
    .set("spark.sql.catalog.iceberg_catalog.io-impl", "org.apache.iceberg.aws.s3.S3FileIO")
    .set("spark.sql.catalog.iceberg_catalog.warehouse", "s3://warehouse/")
    .set("spark.sql.catalog.iceberg_catalog.s3.endpoint", "http://127.0.0.1:9000")

  private val spark = SparkSession.builder.config(sparkConf).getOrCreate()

  spark.sql("DROP TABLE IF EXISTS warehouse.dataset")

  spark.sql("CREATE DATABASE IF NOT EXISTS warehouse")

  private val df = spark.read.parquet("./data/")

  df.show(10,truncate = false)

  df.write.saveAsTable("warehouse.dataset")
}