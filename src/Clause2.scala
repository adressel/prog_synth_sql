package src
import scala.collection.mutable._

object Clause2 {
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()
	
	def populate = {
		rule1
		rule2
		rule3
		rule58
//		rule5and8
//		rule6
	}
	
	def populate2 = {
		
	}
	
	// rule functions create the clauses for each rule
	// at least one attribute variable is true
	def rule1 = {
		val list : MutableList[Variable.Literal] = MutableList()
		for (attr <- AttributeVariable.all) list += ((attr, true))
		clauses += ((ArrayBuffer(new Clause(list.toList)),1))
	}
	
	// at least one condition variable is true
	def rule2 = {
	  	val list : MutableList[Variable.Literal] = MutableList()
		for (attr <- ConditionVariable.all) list += ((attr, true))
		clauses += ((ArrayBuffer(new Clause(list.toList)),2))
	}
	
	// maps attribute variable (select) to output attributes
	def rule3 = {
	  	val tmpClauses : ArrayBuffer[Clause]= ArrayBuffer()
		for (attr <- AttributeVariable.all){
			var contain = false
			for(attrOut <- OutputDesiredVariable.all){
				if (attr.tableName == attrOut.tableName && attr.attrName == attrOut.attrName){
				  val list : MutableList[Variable.Literal] = MutableList()
				  list += new Variable.Literal (attrOut, true)
				  list += new Variable.Literal (attr, false)
				  tmpClauses += new Clause (list.toList)
				  contain = true
				}
			}
			if(!contain)
				tmpClauses += new Clause (List((attr, false)))// add to list
		}
	  	clauses += ((tmpClauses, 3))
	}
	
	def rule58 = {
		val tmpClauses5 : ArrayBuffer[Clause]= ArrayBuffer()
		val tmpClauses8 : ArrayBuffer[Clause]= ArrayBuffer()
		
		val otvMap = OutputVariable.all.map(x => (x.keyVector, x)).toMap
		
		for(cv <- ConditionVariable.all) {
			val query_results = Utility.queryToVector(s"${Data.desired_query} where ${cv.query}").toSet
			val contains_all_matches = otvMap.forall({case (k, v) => query_results.contains(k)})
			if(contains_all_matches) {
				tmpClauses5 += new Clause(List((cv, true)))
				println(s"${cv.print} and")
			}
			else
				tmpClauses8 += new Clause(List((cv, false)))
		}
		clauses += ((tmpClauses5, 5))
		clauses += ((tmpClauses8, 8))
	}
	

}