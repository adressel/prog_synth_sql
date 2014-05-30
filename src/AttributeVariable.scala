package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class AttributeVariable (
	val tableName: String, 
	val attrName: String, 
	val constant : Vector[String],
	val attrType : String
) extends Variable {
	override def toString() = tableName + "  " + attrName + "  " + constant.mkString(",") + "\n"
	val constVector = constant.toVector
}

object AttributeVariable {
	private var attrs : Vector[AttributeVariable] = Vector()
	
	def all = attrs
	
	//creates the Vector of attributes given two tables
	def populate(tableName : Vector[String]) = {
		val x : ArrayBuffer[AttributeVariable] = ArrayBuffer()
		for (table <- tableName)
		{
		    val statement = Data.connection.createStatement();
		    val statement2 = Data.connection.createStatement();
	      // resultSet gets the result of the SQL query
	
		     val  resultSet = statement
		          .executeQuery("SHOW columns FROM "+ table +";")
		     
		     while (resultSet.next())
		     {
		       val test = resultSet.getString("field");
		       val attrType = resultSet.getString("type");
		       val constantSet = statement2.executeQuery("select distinct " + test + " from  "+ table +";");
		       val constVector : ArrayBuffer[String] = ArrayBuffer()
		       while (constantSet.next())
		       {
		         constVector += constantSet.getString(test)
		       }
		      val index = attrType.indexOf("(");
		      val typeStr = 
		        if (index != -1)
		        	attrType.substring(0, index)
		        else 
		            attrType
		       x += new AttributeVariable(table, test, constVector.toVector, typeStr)
		     }
		}
	    attrs = x.toVector
	}
}