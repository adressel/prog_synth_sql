package src
import java.sql.Connection
import scala.collection.mutable.MutableList

class AttributeVariable (
	val tableName: String, 
	val attrName: String, 
	val constant : List[String],
	val attrType : String
) extends Variable {
	override def toString() = tableName + "  " + attrName + "  " + constant.mkString(",") + "\n"
	val constList = constant.toList
}

object AttributeVariable {
	def test = {
		println("hi")
	}
	//creates the list of attributes given two tables
	def populate(connection: Connection, tableName : List[String]) : MutableList[AttributeVariable] = {
		val x : MutableList[AttributeVariable] = MutableList()
		for (table <- tableName)
		{
		    val statement = connection.createStatement();
		    val statement2 = connection.createStatement();
	      // resultSet gets the result of the SQL query
	
		     val  resultSet = statement
		          .executeQuery("SHOW columns FROM "+ table +";")
		     
		     while (resultSet.next())
		     {
		       val test = resultSet.getString("field");
		       val attrType = resultSet.getString("type");
		       val constantSet = statement2.executeQuery("select distinct " + test + " from  "+ table +";");
		       val constList : MutableList[String] = MutableList()
		       while (constantSet.next())
		       {
		         constList += constantSet.getString(test)
		       }
		      val index = attrType.indexOf("(");
		      val typeStr = 
		        if (index != -1)
		        	attrType.substring(0, index)
		        else 
		            attrType
		       x += new AttributeVariable(table, test, constList.toList, typeStr)
		     }
		}
	     x
	}
}