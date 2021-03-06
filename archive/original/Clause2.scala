package src
import scala.collection.mutable._

object Clause2 {
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()
	
	def populate = {
		rule6
		rule58
	}
	
	def rule6 = {
		val tmpClauses6 = OutputVariable.all.map(x => new Clause(List((x, true))))
		val tmpBuffer6 : ArrayBuffer[Clause] = ArrayBuffer()
		tmpBuffer6 ++= tmpClauses6
		clauses += ((tmpBuffer6, 6))
	}
	
	def rule58 = {
		val tmpClauses5 : ArrayBuffer[Clause]= ArrayBuffer()
		val tmpClauses8 : ArrayBuffer[Clause]= ArrayBuffer()
		
		val otvMap = OutputVariable.all.map(x => (x.keyVector, x)).toMap
		
		for(cv <- ConditionVariable.all) {
			
			//print all clauses that contain all matches
			val query_results = Utility.queryToVector(s"${Data.desired_query} where ${cv.query}").toSet
			val contains_all_matches = OutputVariable.all.forall(
				otv => query_results.contains(otv.keyVector)
			)
			if(contains_all_matches)
				println(s"${cv.print} and")
			
			val (matched_otv, unmatched_otv) = OutputVariable.all.partition(
				x => query_results.contains(x.keyVector)
			)
			
			for(otv <- matched_otv) {
				otv.matches += cv
			}
			
			for(otv <- unmatched_otv) {
				tmpClauses8 += new Clause(List((otv, false), (cv, false)))
			}
				
		}
		
		for(otv <- OutputVariable.all) {
			tmpClauses5 += new Clause(List((otv, false)) ::: otv.matches.toList.map(x => (x, true)))
		}
		
		clauses += ((tmpClauses5, 5))
		clauses += ((tmpClauses8, 8))
	}
	

}