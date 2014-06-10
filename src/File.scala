package src
import java.io._
import scala.io._
import scala.collection.mutable.ArrayBuffer

object Printer {
   private val out = new PrintWriter(s"${Data.root}cnf_files/output.cnf")
   
	def print_head = {
	  val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.size} \n"
		out.print(header)
	}
   
	def print_file (ruleNum : Int, clause : ArrayBuffer[Clause])= {
		  out.print(s"c =========  rule $ruleNum  ============\n") 
		  for (rules <- clause){
			  var cnf = ""
			  for (attr <- rules.literals){
				  cnf += ((if(!attr._2) {"-"} else {""}) +  attr._1 + " ")
			  }
			  out.print(cnf + "0\n") 
		  } 
	}
	
	def print_close = {
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