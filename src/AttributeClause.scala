package src
import java.sql.Connection
import scala.collection.mutable.MutableList


class AttributeClause (tableName: String, attrName: String, constant : MutableList[String]) extends Clause {
	override def toString() = tableName + "  " + attrName 
}

object AttributeClause {
	def test = {
		println("hi")
	}
	//creates the list of attributes given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
	    var returnList : List[AttributeClause] = List()
		val statement = connection.createStatement();
	    val statement2 = connection.createStatement();
      // resultSet gets the result of the SQL query

	     val  resultSet = statement
	          .executeQuery("SHOW columns FROM "+tableName1+";");
	  	
	     val x : MutableList[AttributeClause] = MutableList()
	     
	     while (resultSet.next())
	     {
	       val test = resultSet.getString("field");
	       val constantSet = statement2.executeQuery("select distinct " + test + " from  "+tableName1+";");
	       val constList : MutableList[String] = MutableList()
	       while (constantSet.next())
	       {
	         constList += constantSet.getString(test)
	         
	       }
	       x += new AttributeClause(tableName1, test, constList)
	     }
	
	}
}