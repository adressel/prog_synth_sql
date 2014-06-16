package src
import scala.io.Source
import java.io._
import scala.io._
import scala.collection.mutable.ArrayBuffer

object Printer {
//   def print_file = {
//		val out = new PrintWriter(s"${Data.root}sat/cnf_files/output.cnf")
//		val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.size} \n"
//		out.print(header)
//		for (clause <- Clause.clauses){
//		  val ruleNum : Int = clause._2 
//		  out.print(s"c =========  rule $ruleNum  ============\n") 
//		  var cnf = ""
//		  for (rules <- clause._1){
//			  for (attr <- rules.literals){
//				  cnf += ((if(!attr._2) {"-"} else {""}) +  attr._1 + " ")
//			  }
//			  cnf += "0\n"
//		  } 
//		  out.print(cnf)
//		}
//		out.close()
//	}
  
//  def print_file = {
//	    val outputFile = new File(s"${Data.root}sat/cnf_files/output.cnf")
//	    val out = new BufferedWriter(new FileWriter(outputFile))
//		val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.size} \n"
//		out.write(header)
//		for (clause <- Clause.clauses){
//		  val ruleNum : Int = clause._2 
//		  out.write(s"c =========  rule $ruleNum  ============\n") 
//		  var cnf = new StringBuffer
//		  for (rules <- clause._1){
//			  for (attr <- rules.literals){
//				  cnf.append((if(!attr._2) {"-"} else {""}) +  attr._1 + " ")
//			  }
//			  cnf.append("0\n")
//		  } 
//		  out.write(cnf.toString)
//		  out.flush()
//		}
//		out.close()
//	}
  
	def print_file = {
	    val outputFile = new File(s"${Data.root}sat/cnf_files/output.cnf")
	    val out = new BufferedWriter(new FileWriter(outputFile))
		val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.size} \n"
		out.write(header)
		for (clause <- Clause.clauses){
		  val ruleNum : Int = clause._2 
		  out.write(s"c =========  rule $ruleNum  ============\n") 
		  for (rules <- clause._1){
			  for (attr <- rules.literals){
				  out.write((if(!attr._2) {"-"} else {""}) +  attr._1 + " ")
			  }
			  out.write("0\n")
		  } 
		  out.flush()
		}
		out.close()
	}
  
}




object Reader {
	def process_cnf = {
		val result = Source.fromFile(s"${Data.root}sat/results/results.txt").mkString
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