package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class OutputDesiredVariable (
	val tableName: String, 
	val attrName: String 
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
		       val test = resultSet.getString("field")
		       if (test.indexOf(table.toLowerCase()) == 0)
		         x += new OutputDesiredVariable(table, test.substring(table.length()))
		     }
		}
	   attrs = x.toVector
	}
}