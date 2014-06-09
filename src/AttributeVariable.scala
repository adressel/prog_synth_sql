package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

class AttributeVariable (
	val tableName: String, 
	val attrName: String, 
	val constant : Vector[Any],
	val max : Any,
	val min : Any,
	val attrType : String
) extends Variable {
	override def toString() = s"$tableName $attrName $attrType ${constant.mkString(",")} max: $max and min: $min\n"
	def names = s"$tableName.$attrName"
	val constVector = constant.toVector
}

object AttributeVariable {
	private var attrs : Vector[AttributeVariable] = Vector()
	
	def all = attrs
	
	//creates the Vector of attributes given two tables
	def populate = {
		val x : ArrayBuffer[AttributeVariable] = ArrayBuffer()
		var i = 0
		val output_var = OutputVariable.all
		for (table <- Data.table_names)
		{
		   val attributes = Utility.getTableAttrs(table)
		   for (i <- 0 until attributes.length)
		   {
			    val constantSet = Data.connection.createStatement().executeQuery(s"select distinct ${attributes(i)._1} from $table;");
			    val constVector : ArrayBuffer[Any] = ArrayBuffer()
			    while (constantSet.next()){
			    	constVector += constantSet.getObject(attributes(i)._1)
			    }
		     if (attributes(i)._2 == "varchar" || attributes(i)._2 == "date"){
		    	var tmpattr : Set[String] = output_var.map(x => x.tuple(i).toString).toSet
		    	x += new AttributeVariable(table, attributes(i)._1, constVector.toVector,tmpattr.max, tmpattr.last, attributes(i)._2)
		     }
		     else {
		        var tmpattr : Set[Double] = output_var.map(x => (x.tuple(i).toString).toDouble).toSet
		        x += new AttributeVariable(table, attributes(i)._1, constVector.toVector,tmpattr.max, tmpattr.last, attributes(i)._2)
		     }
		   }
		}
	    attrs = x.toVector
	}
}