package src
import java.io.File
import scala.sys.process._


object CNF {
	var wheres : Array[String] = Array()
	var query : String = ""
	var clauses : Array[Int] = Array()
		
	def solve = {
		val result = s"${Data.root}zchaff ${Data.root}cnf_files/output.cnf" !!
		val pattern = "(.*)Random Seed Used".r
		val Some(patternMatch) = pattern.findFirstMatchIn(result)
		clauses = patternMatch.group(1).split(" ").filter(_(0) != '-').map(x => x.toInt)
	}
	
	def post_process = {
		val conditions = clauses.map(x => Variable.all(x-1)).collect{case x: ConditionVariable => x}
		val wheres = conditions.map(x => x.clause)
		query = s"${Data.desired_selects} where ${wheres.mkString(" and \n")}"
	}
	
	def evaluate_correctness = {
		val original = Utility.query_to_vector(Data.desired_query).toSet
		val derived = Utility.query_to_vector(query).toSet
		
		val missed = (original -- derived).size
		val extras = (derived -- original).size
		
		printf(s"Out of ${original.size} tuples, $missed were missed, with $extras extra")
	}
}