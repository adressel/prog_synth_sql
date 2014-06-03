package src
import java.io._
import scala.io._

object Printer {

//	private	val root = "./" // for Ian
	private val root = "/Users/Stephen/Desktop/FeatureCreature" // for Sheng

	
	def printFile = {
		val out = new PrintWriter(s"${root}output.cnf")
		val header = "c output.enc\nc\np cnf "+ Variable.count +" " + Clause.clauses.length + " \n"
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
//	private	val root = "./sat/cnf_files/" // for Ian
	private val root = "/Users/Stephen/Desktop/FeatureCreature" // for Sheng
		
	def readFile = {
		val in = Source.fromFile(s"${root}output.cnf").mkString
	}
}