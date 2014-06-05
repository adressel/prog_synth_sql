package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class AttributeVariable (
	val tableName: String, 
	val attrName: String, 
	val constant : Vector[Any],
	val attrType : String
) extends Variable {
	override def toString() =s"$tableName  $attrName $attrType ${constant.mkString(",")}"
	override def name = s"$tableName.$attrName"
	val constVector = constant.toVector
}

object AttributeVariable {
	private var attrs : Vector[AttributeVariable] = Vector()
	
	def all = attrs
	
	//creates the Vector of attributes given two tables
	def populate(tableName : Vector[String]) = {
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
		       /*
			    *  //val otherConst = Utility.queryToVector(s"select max($attrname), min($attrname), avg($attrname) from $table;");
			    * //constVector ++= otherConst(0).toVector
		        * this part is for other const such as max, min, avg
		        * */
		       x += new AttributeVariable(table, attrname, constVector.toVector, attrType)
		     }
		}
	    attrs = x.toVector
	}
}