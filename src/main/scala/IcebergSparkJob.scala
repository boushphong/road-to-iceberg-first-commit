import org.apache.spark.sql.SparkSession

object IcebergSparkJob extends App {
  private val spark = SparkSession
    .builder()
    .appName("Iceberg Spark Job")
    .master("local")
    .config("spark.sql.extensions", "org.apache.iceberg.spark.extensions.IcebergSparkSessionExtensions")
    .config("spark.sql.catalog.local", "org.apache.iceberg.spark.SparkCatalog")
    .config("spark.sql.catalog.local.catalog-impl", "org.apache.iceberg.jdbc.JdbcCatalog")
    .config("spark.sql.catalog.local.uri", "jdbc:postgresql://localhost:5432/postgres")
    .config("spark.sql.catalog.local.jdbc.user", "postgres")
    .config("spark.sql.catalog.local.jdbc.password", "postgres")
    .config("spark.sql.catalog.my_catalog.jdbc.verifyServerCertificate", value = true)
    .config("spark.sql.catalog.my_catalog.jdbc.useSSL", value = true)
    .config("spark.sql.catalog.local.warehouse", "./spark-warehouse")
    .config("spark.sql.defaultCatalog", "local")
    .config("spark.sql.catalogImplementation", "in-memory")
    .getOrCreate()

  spark.sql("DROP TABLE IF EXISTS warehouse.dataset")

  spark.sql("CREATE DATABASE IF NOT EXISTS dataset")

  private val df = spark.read.parquet("./data/")

  df.show(10,truncate = false)

  df.write.saveAsTable("warehouse.dataset")
}