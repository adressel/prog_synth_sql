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
	
	// map condition variables to output variables
	def rule4and5 = {
		// create a mapping from the OTV vector to the Output Variable
		val otvMap = OutputVariable.all.map(x => (x.keyVector, x)).toMap
		
		for(cv <- ConditionVariable.all) {
			val matches = Utility.queryToVector(OutputVariable.selectQuery + cv.query)
			for(m <- matches) {
				clauses += new Clause(List((cv, false), (otvMap(m), true)))
				otvMap(m).matches += cv
			}
			
			//print for debugging
			if(matches.size > 0) {
				for(m <- matches) {
//					println(m.mkString(", ") + "hi")
					println(otvMap(m))
				}
				println("////////////////////////")
				println("")
			}
		}
		for((v, otv) <- otvMap) {
			val clauseList = List((otv, false)) ::: (otv.matches.map(cv => (cv, true)).toList)
			clauses += new Clause(clauseList)
		}
	}
	
	// map desired output variables to output variables
	def rule6 = {
	   	val result : MutableList[MutableList[Variable.Literal]] = MutableList()
	   	val tmpClauses : ArrayBuffer[Clause]= ArrayBuffer()
	   	for( i <- 0 until OutputDesiredVariable.numForRows  ) {
	   	  result += new MutableList()
	   	}
		for (output <- OutputVariable.all){
			val attr = output.key1
			var query = "select rownum from " + Data.desiredTableName + " where "
			val attrName = OutputDesiredVariable.all
			var i = 0
			val statement = Data.connection.createStatement()
			val tempList : MutableList[String] = MutableList()
 			for( i <- 0 until OutputDesiredVariable.all.length  ) {
				if (attrName(i).attrType.toUpperCase() == "INT")
					tempList += attrName(i).tableName + attrName(i).attrName + " = " + attr(i) + " "
				else 
				    tempList += attrName(i).tableName + attrName(i).attrName + " = \'" + attr(i) + "\' "
			}
			//println(query + tempList.mkString("and "))
			val resultSet = statement.executeQuery(query + tempList.mkString("and ") + ";") 
			if (resultSet.next()){
				val row = resultSet.getInt("rownum")
				result(row - 1) += ((output,true))
			    //println(row + tempList.mkString("and "))
			}
			else tmpClauses += new Clause(List((output, false)))
		}
		for (singleRes <- result) tmpClauses += new Clause(singleRes.toList)
		clauses += ((tmpClauses, 6))
	}
	
	// list undesired output variables
	def rule7 = {
		
	}
}