package src
import scala.collection.mutable._
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.ResultSet

object Utility {
	
	def total_queries = query_count
	var query_count = 0
	
	def time(func: () => Unit) : Double = {
		val now = System.nanoTime
		func()
		val elapse = (System.nanoTime - now) / 1000000000.0
		elapse
	}
	
	//returns a list of primary keys for a table
	def getPrimaryKeys(tableName: String) : Vector[String] = {
		var primaryKeys : ArrayBuffer[String] = ArrayBuffer()
		val keys = Data.connection.getMetaData().getPrimaryKeys(null, null, tableName)
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
	
	//takes query string and returns vector of vector of results
	def queryToVector(query : String) : Vector[Vector[Any]] = {
//		println(query)
		query_count += 1
		val rs = Data.connection.createStatement().executeQuery(query)
		val results : ArrayBuffer[Vector[Any]] = ArrayBuffer()
		while(rs.next) {
			results += resultToVector(rs)
		}
		results.toVector
	}
	
	//creates a resultSet from select (columns) from tableName
	//for instance, with tableName = "album" and columns = List("id", "location")
	//it would return the resultSet from select id, location from album
	def selectAllColumns(tableName: Vector[String], 
			columns: Vector[String]) : ResultSet = {
		val statement = Data.connection.createStatement()
		//println(s"select ${columns.mkString(", ")} from $tableName")
		statement.executeQuery(s"select ${columns.mkString(", ")} from ${tableName.mkString(", ")} ")
	}
	
	def getTableAttrs (tableName : String) : Vector[Tuple2[String, String]] = {
			val results : ArrayBuffer[Tuple2[String,String]] = ArrayBuffer()
			val statement = Data.connection.createStatement()
			val  resultSet = statement
		          .executeQuery("SHOW columns FROM "+ tableName +";")
		     while (resultSet.next()){
		       val attrname = resultSet.getString("field");
		       val attrType = resultSet.getString("type");
		       val index = attrType.indexOf("(");
		       val typeStr = if (index != -1) attrType.substring(0, index)
		    		   			else attrType
		       results += ((attrname, typeStr))
		     }
			results.toVector
	}
}