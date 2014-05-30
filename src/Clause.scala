package src
import scala.collection.mutable.MutableList

class Clause {
	type Literal = Tuple2[Variable, Boolean]
	
	val literals : List[Literal] = List()
	def printString = {
	  //StringBuilder
	  for(literal <- literals) {
	    //if true add - 
	  }
	}
}

object Clause {
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
	def rule4 = {
		
	}
	
	// map output variable to condition variables
	def rule5 = {
		
	}
	
	// map desired output variables to output variables
	def rule6 = {
		
	}
	
	// list undesired output variables
	def rule7 = {
		
	}
}