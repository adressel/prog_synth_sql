package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class AttributeVariable (
	val tableName: String, 
	val attrName: String, 
	val constant : Vector[Any],
	val attrType : String
) {
	override def toString() = tableName + "  " + attrName + "  " + attrType + " " + constant.mkString(",")
	def name = s"$tableName.$attrName"
	val constVector = constant.toVector
}

object AttributeVariable {
	private var attrs : Vector[AttributeVariable] = Vector()
	
	def all = attrs
	
	//creates the Vector of attributes given two tables
	def populate = {
		val tableName = Data.table_names
		val x : ArrayBuffer[AttributeVariable] = ArrayBuffer()
		for (table <- tableName){
		    val attr : Vector[Tuple2[String, String]] = Utility.getTableAttrs(table)
		    for ((attrname,attrType)  <- attr){
			    val statement = Data.connection.createStatement();
			    val constantSet = statement.executeQuery(s"select distinct $attrname from $table;");
			       val constVector : ArrayBuffer[Any] = ArrayBuffer()
			       while (constantSet.next()){
			    	   constVector += constantSet.getObject(attrname)
			       }
			       x += new AttributeVariable(table, attrname, constVector.toVector, attrType)
		     }
		}
	    attrs = x.toVector
	}
}