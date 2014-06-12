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
	override def name = s"$tableName.$attrName"
	val constVector = constant.toVector
}

object AttributeVariable {
	private var attrs : Vector[AttributeVariable] = Vector()
	
	def all = attrs
	
	//creates the Vector of attributes given two tables
	def populate = {
	val x : ArrayBuffer[AttributeVariable] = ArrayBuffer()
		var i = 0
		val output_var = OutputVariable.good
//		output_var.map(x => println(x._1))
		var columnNum = 0
		
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
		     if (attributes(i)._2 == "varchar" || attributes(i)._2 == "date" 
		    	 ||attributes(i)._2 == "char"||attributes(i)._2 == "timestamp"){
		    	var tmpattr : Set[String] = output_var.map(x => x._1(i + columnNum).toString).toSet
//		    	println(attributes(i)._1 + s" ${ tmpattr.reduceLeft((x,y) => if (x < y) x else y)}  " + tmpattr)
		    	x += new AttributeVariable(table, attributes(i)._1, constVector.toVector,tmpattr.max, tmpattr.min, attributes(i)._2)
		     }
		     else {
		        var tmpattr : Set[Double] = output_var.map(x => (x._1(i + columnNum).toString).toDouble).toSet
//		        println(attributes(i)._1 + s" ${ tmpattr.reduceLeft((x,y) => if (x > y) x else y)}  " + tmpattr)
		        x += new AttributeVariable(table, attributes(i)._1, constVector.toVector,tmpattr.max, tmpattr.min, attributes(i)._2)
		     }
		   }
		   columnNum += attributes.length
		}
	    attrs = x.toVector
	}
}