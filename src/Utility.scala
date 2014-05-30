package src
import scala.collection.mutable._
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.ResultSet

object Utility {
	//returns a list of primary keys for a table
	def getPrimaryKeys(connection: Connection, tableName: String) : Vector[String] = {
		var primaryKeys : ArrayBuffer[String] = ArrayBuffer()
		val keys = connection.getMetaData().getPrimaryKeys(null, null, tableName)
		while(keys.next()) {
			primaryKeys += keys.getString("COLUMN_NAME")
		}
		primaryKeys.toVector
	}
	
	//converts a resultSet (that is assumed to be valid) to a List[Any]
	def resultToVector(result : ResultSet) : Vector[Any] = {
		val resultList : ArrayBuffer[Any] = ArrayBuffer()
		
		val columnCount = result.getMetaData().getColumnCount()
		for(i <- 1 to columnCount) {
			val columnVal = result.getObject(i)
			resultList += (if(columnVal == null) "null" else columnVal)
		}
		resultList.toVector
	}
	
	//creates a resultSet from select (columns) from tableName
	//for instance, with tableName = "album" and columns = List("id", "location")
	//it would return the resultSet from select id, location from album
	def selectAllColumns(connection: Connection, tableName: String, 
			columns: Vector[String]) : ResultSet = {
		val statement = connection.createStatement()
		statement.executeQuery(s"select ${columns.mkString(", ")} from $tableName")
	}
}