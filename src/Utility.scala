package src
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.ResultSet

object Utility {
	//returns a list of primary keys for a table
	def getPrimaryKeys(connection: Connection, tableName: String) = {
		val primaryKeys : List[String] = List()
		val keys = connection.getMetaData().getPrimaryKeys(null, null, tableName)
		while(keys.next()) {
			primaryKeys :+ keys.getString("COLUMN_NAME")
		}
		primaryKeys
	}
	
	//converts a resultSet (that is assumed to be valid) to a List[Any]
	def resultToList(result : ResultSet) = {
		val columnCount = result.getMetaData().getColumnCount()
	}
	
	//creates a resultSet from select (columns) from tableName
	//for instance, with tableName = "album" and columns = List("id", "location")
	//it would return the resultSet from select id, location from album
	def selectAllColumns(connection: Connection, tableName: String, columns: List[String]) = {
		val statement = connection.createStatement()
		statement.executeQuery(s"select ${columns.mkString.split(", ")} from $tableName")
	}
}