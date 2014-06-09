package src
import scala.collection.mutable._
import java.sql.Connection

class OutputVariable (
	val key: Vector[Any]
) extends Variable {
	val matches : ArrayBuffer[ConditionVariable] = ArrayBuffer()
	
	override def print() = {
		s"id: $id key1:${key.mkString(", ")} "
	}

	def keyVector = key
}

class OutputVariableConst (
	val table_name : String,
	val attr_name : String,
	val Max : Any,
	val Min : Any
) extends Variable {
	override def toString() = {
		s"$table_name\.attr_name have max: $Max and min: $Min"
	}
}

object OutputVariable {
	var keys : Vector[Tuple2[String,String]] = Vector()
	private var otvs : Vector[OutputVariable] = Vector()
	private var expanded_table : Vector[Vector[Any]] = Vector()
	
	def table = expanded_table
	var desired_tuples : Vector[Vector[Vector[Any]]] = Vector()
	
	def all = otvs
	def getKey = keys
	
	
	def get_types = {
		for(attr <- table(0)){
			attr match {
				case x : String => println(x + " : string")
				case y : Int => println(y + " : int")
				case z : Double => println(z + " : double")
				case a : Any => println(a + " : date")
			}
		}
	}
	
	def populate = {
//		expanded_table = Utility.query_to_vector(Data.desired_query)
//		otvs = Utility.query_to_vector(Data.desired_query).map(x => new OutputVariable(x))
		
		val desired_tuples_vector = Utility.query_to_vector(Data.desired_query)
		val desired_table = desired_tuples_vector.map(x => (x.zip(Data.desired_attr_names)))

		desired_tuples = for(tuple <- desired_table) yield {
			val tuple_conditions = for(attr <- tuple) yield {
				attr._1 match {
					case x : String => s"${attr._2} = " + "\"" + x + "\""
					case x : Any => s"${attr._2} = $x"
				}
			}
			val tuple_match_query = tuple_conditions.mkString(" and ")
//			println(s"select * from album, usr where $tuple_match_query")
			Utility.query_to_vector(s"select * from ${Data.table_names.mkString(", ")} where $tuple_match_query")
		}
		expanded_table = desired_tuples.flatten
	}
}