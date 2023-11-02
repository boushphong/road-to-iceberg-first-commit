## Getting started

**For interactive notebook:**
- Install `coursier` from https://get-coursier.io/docs/cli-installation
- Run `install_scala_jupyter_kernel.sh`

## The Idea of Iceberg
> Iceberg is a high-performance format for huge analytic tables.

The idea of Iceberg is to create metadata file for a table, so that it enables version control, schema evolution ... and query engine can use these files to optimize their reads performance. To increase reads performance, statistics about a table is stored which Query Enginer can use to avoid reading unnecessary data. Iceberg is made to replace Hive, and uses file-based metadata instead of directory-based metadata.

## Underlying Implementation of Iceberg

To use Iceberg, we need to initiate an Iceberg catalog. The primary high level requirement for a catalog implementation to work as an Iceberg catalog is to map a table path (e.g., “db1.table1”) to the file path of the metadata file that has the table’s current state.

Iceberg catalog recommends a file system to provide a file/object rename operation that is atomic to prevent data loss when concurrent writes occur.

There are catalog implementations such as:
- Hadoop Catalog (Atomic)
- Object Storage (S3) (Non-Atomic)
- JDBC (Atomic)
- Nessie (Atomic)
- ...

By using one of these catalogs, Query Engine like Spark would use the catalog to retrieve information about a table, then plan its execution accordingly to be more efficient.
