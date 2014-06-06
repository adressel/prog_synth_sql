package src
import scala.collection.mutable._
class Clause (
	//Variable.Literal is just a Tuple2[Variable, Boolean]
	val literals : Vector[Variable.Literal]
) {
}

object Clause {
	def all = clauses
	def size = clauses.map(x => x._1.size).sum
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()

	def populate = {
		rule6
		rule58
	}
	
	def rule6 = {
		val tmpClauses6 = OutputVariable.all.map(x => new Clause(Vector((x, true))))
		val tmpBuffer6 : ArrayBuffer[Clause] = ArrayBuffer()
		tmpBuffer6 ++= tmpClauses6
		clauses += ((tmpBuffer6, 6))
	}
	
	def rule58 = {
		val tmpClauses5 : ArrayBuffer[Clause]= ArrayBuffer()
		val tmpClauses8 : ArrayBuffer[Clause]= ArrayBuffer()
		
		val otvMap = OutputVariable.all.map(x => (x.keyVector, x)).toMap
		
		for(cv <- ConditionVariable.all) {
			val query_results = Utility.query_to_vector(s"${Data.desired_query} where ${cv.query}").toSet

			val (matched_otv, unmatched_otv) = OutputVariable.all.partition(
				x => query_results.contains(x.keyVector)
			)
			
			for(otv <- matched_otv) {
				otv.matches += cv
			}
			
			for(otv <- unmatched_otv) {
				tmpClauses8 += new Clause(Vector((otv, false), (cv, false)))
			}
				
		}
		
		for(otv <- OutputVariable.all) {
			tmpClauses5 += new Clause(Vector((otv, false)) ++ otv.matches.map(x => (x, true)))
		}
		
		clauses += ((tmpClauses5, 5))
		clauses += ((tmpClauses8, 8))
	}
	
	def printQuery = {
		
		println("")
		println("")
		val otvSet = OutputVariable.all.map(x => x.keyVector).toSet
		
		for(cv <- ConditionVariable.all) {
			//print all clauses that contain all matches
			val query_results = Utility.query_to_vector(s"${Data.desired_query} where ${cv.query}").toSet
			if((otvSet -- query_results).size == 0) {
				println(s"${cv.query} and")
			}
		}
	}

}