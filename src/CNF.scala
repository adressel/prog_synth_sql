package src
import java.io.File
import scala.sys.process._


object CNF {
	def solve = {
		val result = "zchaff sat/cnf_files/output.cnf" !!
		val pattern = "(.*)Random Seed Used".r
		val Some(patternMatch) = pattern.findFirstMatchIn(result)
		val clauses = patternMatch.group(1).split(" ").filter(_(0) != '-').map(x => x.toInt)
		val conditions = clauses.map(x => Variable.all(x-1)).filter(_.isInstanceOf[ConditionVariable])
		val wheres = conditions.map(x => x.print).mkString(", ")
		println("\n======== QUERY ============")
		println(s"${Data.desired_query} where $wheres")
	}
}