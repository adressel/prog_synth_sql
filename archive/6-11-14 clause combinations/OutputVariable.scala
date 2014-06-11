package src
import scala.collection._
import java.sql.Connection

class OutputVariable (
	val tuple: Vector[Any]
) extends Variable {
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
			val query = s"select * from ${Data.desired_tables} where $tuple_match_query"
			val result_tuples = Utility.query_to_vector(query)
			for(result_tuple <- result_tuples) yield new OutputVariable(result_tuple)
		}
		
		// RULE 1
		val clause_buffer : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
		for(otv_group <- otv_groups) {
			clause_buffer += new Clause(otv_group.map(x => (x.id, true)))
		}
		Clause.clauses += ((clause_buffer, 1))
		
		otvs = otv_groups.flatten.toSet
	}
}