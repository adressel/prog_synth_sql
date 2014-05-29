package src
import java.sql.Connection
import java.sql.DatabaseMetaData

object Utility {
	def getPrimaryKeys(connection: Connection, tableName: String) = {
		val primaryKeys : List[String] = List()
		val keys = connection.getMetaData().getPrimaryKeys(null, null, tableName)
		while(keys.next()) {
			primaryKeys :+ keys.getString("COLUMN_NAME")
		}
		primaryKeys
	}
}