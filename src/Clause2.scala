package src
import scala.collection.mutable._

object Clause2 {
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()
	
	def populate = {
//		rule1
//		rule2
//		rule3
		rule4
		rule6
		rule58
	}
	
	def rule4 = {
		val tmpClauses6 = ConditionVariable.all.toList.map(x => (x, true))
		val tmpBuffer6 : ArrayBuffer[Clause] = ArrayBuffer()
		tmpBuffer6 += new Clause(tmpClauses6)
		clauses += ((tmpBuffer6, 4))
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
			
			if(matched_otv.size > 0) {
				tmpClauses5 += new Clause(List((cv, false)) ::: matched_otv.toList.map(x => (x, true)))
			}
			
			for(otv <- unmatched_otv) {
				tmpClauses8 += new Clause(List((cv, false), (otv, false)))
			}
				
		}
		clauses += ((tmpClauses5, 5))
		clauses += ((tmpClauses8, 8))
	}
	

}