package src
import scala.collection.mutable._
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.ResultSet

object Utility {
	
	def total_queries = query_count
	var query_count = 0
	var query_time = 0.0
	
	def time(func: () => Unit) : Double = {
		val now = System.nanoTime
		func()
		val elapse = (System.nanoTime - now) / 1000000000.0
		elapse
	}
	
	//returns a list of primary keys for a table
	def get_primary_keys(tableName: String) : Vector[String] = {
		var primaryKeys : ArrayBuffer[String] = ArrayBuffer()
		val keys = Data.connection.getMetaData().getPrimaryKeys(null, null, tableName)
		while(keys.next()) {
			primaryKeys += keys.getString("COLUMN_NAME")
		}
		primaryKeys.toVector
	}
	
	//converts a resultSet (that is assumed to be valid) to a List[Any]
	def result_to_vector(result : ResultSet) : Vector[Any] = {
		val resultList : ArrayBuffer[Any] = ArrayBuffer()
		
		val columnCount = result.getMetaData().getColumnCount()
		for(i <- 1 to columnCount) {
			val columnVal = result.getObject(i)
			resultList += (if(columnVal == null) "null" else columnVal)
		}
		resultList.toVector
	}
	
	//takes query string and returns vector of vector of results
	def query_to_vector(query : String) : Vector[Vector[Any]] = {
//		println(query)
		query_count += 1
		val now = System.nanoTime
		val rs = Data.connection.createStatement().executeQuery(query)
		query_time += (System.nanoTime - now) / 1000000000.0
		val results : ArrayBuffer[Vector[Any]] = ArrayBuffer()
		while(rs.next) {
			results += result_to_vector(rs)
		}
		results.toVector
		
	}
	
	def execute(statement : String) {
	  Data.connection.createStatement.execute(statement)
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