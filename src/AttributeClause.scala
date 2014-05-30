package src
import java.sql.Connection
import scala.collection.mutable.MutableList

class AttributeClause (tableName: String, attrName: String, constant : MutableList[String]) extends Clause {
	override def toString() = tableName + "  " + attrName + "  " + constant.mkString(",") + "\n"
	val constList = constant.toList
}

object AttributeClause {
	def test = {
		println("hi")
	}
	//creates the list of attributes given two tables
	def populate(connection: Connection, tableName : List[String]) : MutableList[AttributeClause] = {
		val x : MutableList[AttributeClause] = MutableList()
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
		       val constantSet = statement2.executeQuery("select distinct " + test + " from  "+ table +";");
		       val constList : MutableList[String] = MutableList()
		       while (constantSet.next())
		       {
		         constList += constantSet.getString(test)
		       }
		       x += new AttributeClause(table, test, constList)
		     }
		}
	     x
	}
}