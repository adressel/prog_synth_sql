package src
import scala.collection._
import java.sql.Connection

// the OutputVariable class/object is responsible for representing possible outputs
// for a query.  

class OutputVariable (
	val tuple: Vector[Any]
) extends Variable {
}

// OutputVariableConst is a class that keeps track of maximum and minimum values
// for each attribute of the output cross product
class OutputVariableConst (
	val table_name : String,
	val attr_name : String,
	val Max : Any,
	val Min : Any
) {
	override def toString() = {
		s"$table_name\.attr_name have max: $Max and min: $Min"
	}
}

// OutputVariables are actually not stored as OutputVariables.  Rather, they are just
// container classes that hold an Integer (variable id) and a Vector[Any] (the tuple)
object OutputVariable {
	// a map from Vector[Any] -> Variable ids that are "good" or desired
	private var good_otvs : Map[Vector[Any], Int] = Map()
	// similar to bad_otvs, but for undesired output tuples
	private var bad_otvs : Map[Vector[Any], Int] = Map()

	def good = good_otvs
	
	def bad = bad_otvs
	
	def all = good_otvs ++ bad_otvs
	
	// call populate to generate all output tuples and their corresponding variables
	// in addition, part of the rules encoding (rule 1) is best done while populating
	// the output tuple variables, so it is done here as well.
	def populate = {
		// create a temporary arraybuffer for rule 1 clauses
		val clause_buffer_1 : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
	  
		val select_all_query = s"select * from ${Data.desired_tables}"
		val all_tuples = Utility.query_to_vector(select_all_query)

		// create an OutputVariable for each tuple in the query select * 
		val new_otvs = all_tuples.map(new OutputVariable(_))
		// otv_map is a map from tuple to id, that essentially "strips" the important
		// values from an OutputVariable and converts it to a more accessible map format
		val otv_map = new_otvs.map(x => (x.tuple, x.id)).toMap

		val desired_tuples_vector = Utility.query_to_vector(Data.desired_query)
		
		val desired_table = desired_tuples_vector.map(x => (x.zip(Data.desired_attr_names)))

		// iterate through desired tuples
		for(tuple <- desired_table){
			// tuple conditions converts each attribute in the table to a query.
			// for instance, if there is a desired query containing Age = 40, and Name = "Bob",
			// tuple_conditions will generate a Vector[String] containing ["Age = 40", "Name=\"Bob\""]
			val tuple_conditions = for(attr <- tuple) yield {
				attr._1 match {
					case x : String => s"${attr._2} = " + "\"" + x + "\""
					case x : Any => s"${attr._2} = $x"
				}
			}
			// convert tuple_conditions to a query
			val tuple_match_query = tuple_conditions.mkString(" and ")
			val query = s"select * from ${Data.desired_tables} where $tuple_match_query"
			// get the Vector[Any] that represents all otvs that match this desired tuple (there could be multiples!)
			val result_tuples = Utility.query_to_vector(query)
			
			good_otvs ++= result_tuples.map(x => (x, otv_map(x)))
			// update this new clause
			clause_buffer_1 += new Clause(result_tuples.map(x => otv_map(x)).mkString(" "))
		}
		bad_otvs = otv_map -- good_otvs.map(x => x._1)
		// add the rule 1 clauses to the Clause object
		Clause.clauses += ((clause_buffer_1, 1))
	}
	
}