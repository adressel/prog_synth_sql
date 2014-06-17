package src
import scala.io.Source
import java.io._
import scala.io._
import scala.collection.mutable.ArrayBuffer

object Printer {
	
	def print_file = {
	    val outputFile = new File(s"${Data.root}sat/cnf_files/output.cnf")

	    val out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream( outputFile) ), 8000000 )
		val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.size} \n"
		out.write(header)
		for (clause <- Clause.clauses){
		  val ruleNum : Int = clause._2 
		  out.write(s"c =========  rule $ruleNum  ============\n")
		  for (rules <- clause._1){
		      out.write(rules.literals)
			  out.write(" 0\n")
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