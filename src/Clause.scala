package src
import scala.collection.mutable._
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
		for (attr <- AttributeVariable.all) yield{
			(attr, true)
		}
	}
	
	// at least one condition variable is true
	def rule2 = {
		for (attr <- ConditionVariable.all) yield{
			(attr, true)
		}
	}
	
	// maps attribute variable (select) to output attributes
	def rule3 = {
	  val attrList : MutableList[Tuple2[Variable, Boolean]] = MutableList()
		for (attr <- AttributeVariable.all){
			var contain = false
			for(attrOut <- OutputDesiredVariable.all){
				if (attr.tableName == attrOut.tableName && attr.attrName == attrOut.attrName){
				  attrList += (attr, true)
				  contain = true
				}
			}
			if(!contain)
				(attr, false)// add to list
		}
		
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