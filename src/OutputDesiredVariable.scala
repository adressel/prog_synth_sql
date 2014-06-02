package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class OutputDesiredVariable (
	val tableName: String, 
	val attrName: String,
	val attrType : String
)extends Variable {
	override def toString() = tableName + "  " + attrName +  "\n"
}

object OutputDesiredVariable {
	private var attrs : Vector[OutputDesiredVariable] = Vector()
	
	def all = attrs
	
	//creates the Vector of attributes given two tables
	def populate(tableNames : Vector[String], output : String) = {
		val x : ArrayBuffer[OutputDesiredVariable] = ArrayBuffer()
		for (table <- tableNames)
		{
		    val statement = Data.connection.createStatement()
		    // resultSet gets the result of the SQL query
		    val  resultSet = statement
		          .executeQuery("SHOW columns FROM "+ output +";") 
		     while (resultSet.next())
		     {
		       val attr = resultSet.getString("field")
		       val attrType = resultSet.getString("type")
		       if (attr.indexOf(table.toLowerCase()) == 0)
		       {println(attr.substring(table.length()))
		         x += new OutputDesiredVariable(table, attr.substring(table.length()), attrType)
		       }
		     }
		}
	   attrs = x.toVector
	}
}