package src
import scala.collection._
import java.sql.Connection

class OutputVariable (
	val tuple: Vector[Any]
) extends Variable {
	val matches : mutable.Set[ConditionVariable] = mutable.Set()
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
	private var otvs : Set[OutputVariable] = Set()
	
	def all = otvs
	
	def populate = {
		val desired_tuples_vector = Utility.query_to_vector(Data.desired_query)
		val desired_table = desired_tuples_vector.map(x => (x.zip(Data.desired_attr_names)))
		val otv_groups = for(tuple <- desired_table) yield {
			val tuple_conditions = for(attr <- tuple) yield {
				attr._1 match {
					case x : String => s"${attr._2} = " + "\"" + x + "\""
					case x : Any => s"${attr._2} = $x"
				}
			}
			val tuple_match_query = tuple_conditions.mkString(" and ")
			val result_tuples = Utility.query_to_vector(s"select * from album, usr where $tuple_match_query")
			for(result_tuple <- result_tuples) yield new OutputVariable(result_tuple)
		}
		
		for(otv_group <- otv_groups) {
			// RULE 1 SOON
		}
		
		otvs = otv_groups.flatten.toSet
	}
}