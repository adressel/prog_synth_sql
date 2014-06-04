package src
import scala.collection.mutable._
class Clause (
	//Variable.Literal is just a Tuple2[Variable, Boolean]
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
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()
	
	def populate = {
		rule1
		rule2
		rule3
		new_rule_using_new_otv
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
	
	def new_rule_using_new_otv = {
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
	
	def rule5and8 = {
		// create a mapping from the OTV vector to the Output Variable
		val tmpClauses5 : ArrayBuffer[Clause]= ArrayBuffer()
		val tmpClauses8 : ArrayBuffer[Clause]= ArrayBuffer()
		
		val otvMap = OutputVariable.all.map(x => (x.keyVector, x)).toMap
		
		for(cv <- ConditionVariable.all) {
			val query_results = Utility.queryToVector(s"${Data.desired_query} where ${cv.query}")
				
			
			// OLD STUFF
			for(result <- query_results) {
				//old rule 5
				// clauses += new Clause(List((cv, false), (otvMap(m), true)))
				otvMap(result).matches += cv
			}
		}
		
		for((_, otv) <- otvMap) {
			// RULE 5
			val clauseList = List((otv, false)) ::: (otv.matches.map(cv => (cv, true)).toList)
			tmpClauses5 += new Clause(clauseList)
			
			
			// RULE 8
			// create a set of all condition variables
			val wSet = ConditionVariable.all.toSet
			// iterate through the set difference of the matches (all cvs that don't match)
			for(cv <- wSet -- otv.matches) {
				tmpClauses8 += new Clause(List((otv, false), (cv, false)))
			}
		}
		clauses += ((tmpClauses5, 5))
		clauses += ((tmpClauses8, 8))
	}
	
	// map desired output variables to output variables
	def rule6 = {
	   	val result : MutableList[MutableList[Variable.Literal]] = MutableList()
	   	val tmpClauses : ArrayBuffer[Clause]= ArrayBuffer()
	   	for( i <- 0 until OutputDesiredVariable.numForRows  ) {
	   	  result += new MutableList()
	   	}
		for (output <- OutputVariable.all){
			var query = "select rownum from " + Data.desiredTableName + " where "
			val attrName = OutputDesiredVariable.all
			val tempList : MutableList[String] = MutableList()
 			for( i <- 0 until attrName.length  ) {
 			  tempList += attrName(i).tableName + attrName(i).attrName + (
				if (attrName(i).attrType.toUpperCase() == "INT") " = " + output.key1(i) + " "
					else " = \'" + output.key1(i) + "\' ")
			}
			val statement = Data.connection.createStatement()
			val resultSet = statement.executeQuery(query + tempList.mkString("and ") + ";") 
			if (resultSet.next()) result(resultSet.getInt("rownum") - 1) += ((output,true))
			else tmpClauses += new Clause(List((output, false)))
		}
		for (singleRes <- result) tmpClauses += new Clause(singleRes.toList)
		clauses += ((tmpClauses, 6))
	}
}