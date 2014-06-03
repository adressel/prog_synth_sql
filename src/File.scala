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
	private	val root = "./sat/results/" // for Ian
//	private val root = "/Users/Stephen/Desktop/FeatureCreature" // for Sheng
	def readFile = {
		val resultsTxt = Source.fromFile(s"${root}results.txt").mkString
		val pattern = "(.*)Random Seed Used".r
		val Some(patternMatch) = pattern.findFirstMatchIn(resultsTxt)
		val clauses = patternMatch.group(1).split(" ").filter(_(0) != '-').map(x => x.toInt)
		val conditions = clauses.map(x => Variable.all(x-1)).filter(_.isInstanceOf[ConditionVariable])
		val attrs = clauses.map(x => Variable.all(x-1)).filter(_.isInstanceOf[AttributeVariable])
		val selects = attrs.map(x => x.name).mkString(", ")
		val wheres = conditions.map(x => x.print).mkString(" and \n")
		println(s"select $selects from ")
	}
}