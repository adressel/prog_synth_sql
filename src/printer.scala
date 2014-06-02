package src
import java.io._

object printer {
	//private	val root = "C:/Users/laian_000/Documents/GitHub/FeatureCreature" // for Ian
	private val root = "/Users/Stephen/Desktop/FeatureCreature" // for Sheng
	
	def printFile = {
		val out = new PrintWriter(s"${root}results.txt")
		val header = "c  output.enc\n c\n p cnf "+ Variable.count +" " + Clause.clauses.length + " \n"
		out.print(header)
		for (clause <- Clause.clauses){
		  var cnf = ""
		  for (attr <- clause.literals){
			  cnf += ((if(!attr._2) {"-"} else {""}) +  attr._1.id + " ")
		  }
		  //println(cnf + "0\n")
		  out.print(cnf + "0\n") 
		}
		out.close()
	}
}