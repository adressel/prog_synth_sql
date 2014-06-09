package src
import java.io._
import scala.io._

object Printer {
	def print_file = {
		val out = new PrintWriter(s"${Data.root}cnf_files/output.cnf")
		val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.clauses.length} \n"
		out.print(header)
		for (clause <- Clause.clauses){
		  val ruleNum : Int = clause._2 
		  out.print(s"c =========  rule $ruleNum  ============\n") 
		  for (rules <- clause._1){
			  var cnf = ""
			  for (attr <- rules.literals){
				  cnf += ((if(!attr._2) {"-"} else {""}) +  attr._1.id + " ")
			  }
			  out.print(cnf + "0\n") 
		  } 
		}
		out.close()
	}
}

object Reader {
	def process_cnf = {
		val result = Source.fromFile(s"${Data.root}results/results.txt").mkString
		val pattern = "(.*)Random Seed Used".r
		val Some(patternMatch) = pattern.findFirstMatchIn(result)
		val clauses = patternMatch.group(1).split(" ").filter(_(0) != '-').map(x => x.toInt)
//		println(clauses.mkString(", "))
		val conditions = clauses.map(x => Variable.all(x-1)).filter(_.isInstanceOf[ConditionVariable])
//		val attrs = clauses.map(x => Variable.all(x-1)).filter(_.isInstanceOf[AttributeVariable])
//		val selects = attrs.map(x => x.name).mkString(", ")
		val wheres = conditions.map(x => x.print).mkString(" and \n")
		println("\n======== QUERY ============")
		println(s"${Data.desired_query} where $wheres")
	}
}