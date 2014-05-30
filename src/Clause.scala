package src

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
		
	}
	
	// at least one condition variable is true
	def rule2 = {
		
	}
	
	// maps attribute variable (select) to output attributes
	def rule3 = {
		
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