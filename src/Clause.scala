package src
import scala.collection.mutable.ArrayBuffer

class Clause (
	val literals : List[Variable.Literal]
) {
	def printString = {
	  //StringBuilder
	  for(literal <- literals) {
	    //if true add - 
	  }
	}
}

object Clause {
	
	val clauses : ArrayBuffer[Clause] = ArrayBuffer()
	
	// rule functions create the clauses for each rule
	// at least one attribute variable is true
	def rule1 = {
		
	}
	
	// at least one condition variable is true
	def rule2 = {
		
	}
	
	// maps attribute variable (select) to output attributes
	def rule3 = {
		
	}
	
	// map condition variables to output variables
	def rule4and5 = {
		// create a map from tuple(pKeys, pKeys) to output Variables (inefficient, but no other way atm)
		val otvMap = OutputVariable.all.map(x => (x.keyVector, x)).toMap
		
		for(cv <- ConditionVariable.all) {
			val matches = Utility.queryToVector(OutputVariable.selectQuery + cv.query)
			for(m <- matches) {
				clauses :+ new Clause(List((cv, false), (otvMap(m), true)))
				otvMap(m).matches :+ cv
			}
		}
		
		for((v, otv) <- otvMap) {
			val newClause = List((otv, false)) ::: (otv.matches.map(cv => (cv, true)).toList)
			clauses :+ newClause
		}
	}
	
	// map desired output variables to output variables
	def rule6 = {
		
	}
	
	// list undesired output variables
	def rule7 = {
		
	}
}