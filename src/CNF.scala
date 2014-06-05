package src
import java.io.File
import scala.sys.process._


object CNF {
	var wheres : Array[String] = Array()
	var query : String = ""
	var clauses : Array[Int] = Array()
		
	def solve = {
		val result = "zchaff sat/cnf_files/output.cnf" !!
		val pattern = "(.*)Random Seed Used".r
		val Some(patternMatch) = pattern.findFirstMatchIn(result)
		clauses = patternMatch.group(1).split(" ").filter(_(0) != '-').map(x => x.toInt)
	}
	
	def post_process = {
		val conditions = clauses.map(x => Variable.all(x-1)).filter(_.isInstanceOf[ConditionVariable])
		wheres = conditions.map(x => x.print)
		query = s"${Data.desired_query} where ${wheres.mkString(" and ")}"
	}
	
	def evaluate_correctness = {
		val original = Utility.queryToVector(Data.desired_query + Data.desired_query_where).toSet
		val derived = Utility.queryToVector(query).toSet
		
		val missed = (original -- derived).size
		val extras = (derived -- original).size
		
		printf(s"Out of ${original.size} tuples, $missed were missed, with $extras extra")
	}
}