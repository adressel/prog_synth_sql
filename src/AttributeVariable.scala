package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class AttributeVariable (
	val tableName: String, 
	val attrName: String, 
	val constant : Vector[Any],
	val attrType : String
) extends Variable {
	override def toString() = tableName + "  " + attrName + "  " + constant.mkString(",")
	def name = s"$tableName.$attrName"
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
		       val attrname = resultSet.getString("field");
		       val attrType = resultSet.getString("type");
		       val index = attrType.indexOf("(");
		       val typeStr = if (index != -1) attrType.substring(0, index)
		    		   			else attrType
		       val constantSet = statement2.executeQuery(s"select distinct $attrname from $table;");
		       val constVector : ArrayBuffer[Any] = ArrayBuffer()
		       while (constantSet.next()){
		        	 constVector += constantSet.getObject(attrname)
		       }
		       val otherConst = Utility.queryToVector(s"select max($attrname), min($attrname), avg($attrname) from $table;");
		       constVector ++= otherConst(0).toVector
		       
		       x += new AttributeVariable(table, attrname, constVector.toVector, typeStr)
		       println(constVector.toVector)
		     }
		}
	    attrs = x.toVector
	}
}