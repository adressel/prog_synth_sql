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
	private var good_otvs : Map[Vector[Any], Int] = Map()
	private var bad_otvs : Map[Vector[Any], Int] = Map()
	def good = good_otvs
	def bad = bad_otvs
	def all = good_otvs ++ bad_otvs
	
	def populate = {
		val select_all_query = s"select * from ${Data.desired_tables}"
		val all_tuples = Utility.query_to_vector(select_all_query)
		val new_otvs = all_tuples.map(new OutputVariable(_))
		val otv_map = new_otvs.map(x => (x.tuple, x.id)).toMap
		
		val desired_tuples_vector = Utility.query_to_vector(Data.desired_query)
		val desired_table = desired_tuples_vector.map(x => (x.zip(Data.desired_attr_names)))
		 
		for(tuple <- desired_table){
			val tuple_conditions = for(attr <- tuple) yield {
				attr._1 match {
					case x : String => s"${attr._2} = " + "\"" + x + "\""
					case x : Any => s"${attr._2} = $x"
				}
			}
			val tuple_match_query = tuple_conditions.mkString(" and ")
			val query = s"select * from ${Data.desired_tables} where $tuple_match_query"
			val result_tuples = Utility.query_to_vector(query)
			for (desired_otv <- result_tuples)
			{
				if (otv_map.contains(desired_otv))
					good_otvs += ((desired_otv, otv_map(desired_otv)))
			}
		}
		bad_otvs = otv_map.filter(x => !good_otvs.contains(x._1))
	}
}