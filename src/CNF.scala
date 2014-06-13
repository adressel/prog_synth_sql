package src
import java.io.File
import scala.collection.mutable.ArrayBuffer
import scala.sys.process._


object CNF {
	var wheres : Array[String] = Array()
	var query : String = ""
	var clauses : Array[Int] = Array()
	var runtime = 0.0
		
	def solve = {
		val result = s"${Data.root}zchaff ${Data.root}sat/cnf_files/output.cnf" !!
		
		val Some(clause_list) = "(.*)Random Seed Used".r.findFirstMatchIn(result)
		clauses = clause_list.group(1).split(" ").filter(x => x.length > 0 && x(0) != '-').map(x => x.toInt)
		if(clauses.size == 0) {
		  println("No solution found!  Printing WHERE clauses:")
		  println("")
		  ConditionVariable.all.map(_.clause).foreach(println)
		}
		
		val Some(runtime_match) = """Total Run Time\s*(\d+.?\d*)""".r.findFirstMatchIn(result)
		runtime = runtime_match.group(1).toDouble
	}
	
	def evaluate_correctness = {
		val original = Utility.query_to_vector(Data.desired_query).toSet
		val derived = Utility.query_to_vector(query).toSet
		
		val missed = (original -- derived).size
		val extras = (derived -- original).size
		
		println(s"Out of ${original.size} tuples, $missed were missed, with $extras extra")
		println(s"query : ${Data.desired_query}")
	}
	
	def post_process : String = {
		val original = Utility.query_to_vector(Data.desired_query).toSet
		val conditions = clauses.map(x => Variable.all(x-1)).collect{case x: ConditionVariable => x}
		val wheres = conditions.map(x => x.clause)
		query = s"${Data.desired_selects} where ${wheres.mkString(" and \n")}"
		if ((original -- Utility.query_to_vector(query).toSet).size != 0 
		    || (Utility.query_to_vector(query).toSet -- original).size != 0)
			return "The derived query we get is wrong! \n" + query //to check whether the derived query is right
			
		val crucial_clauses : ArrayBuffer[ConditionVariable] = ArrayBuffer()
		var i = 0 
		for (i <-0 until (conditions.length - 1))
		{
		  val test_where = remove(conditions, i).map(x => x.clause)
		  val test_query = s"${Data.desired_selects} where ${test_where.mkString(" and \n")}"
		  val derived = Utility.query_to_vector(test_query).toSet
		  if ((derived -- original).size != 0){
		    crucial_clauses += conditions(i)
		  }
		}
		
		val crucial_wheres = crucial_clauses.map(x => x.clause)
		query = s"${Data.desired_selects} where ${crucial_wheres.mkString(" and \n")}"
		if ((Utility.query_to_vector(query).toSet -- original).size != 0){
		   //conditions.filter(x => !crucial_clauses.contains(x)).map(x => println(x.clause))
		   for (combination <- conditions.filter(x => !crucial_clauses.contains(x)).
		       toSet[ConditionVariable].subsets.map(_.toList).toList){
		     if (!combination.isEmpty){
		    	 val query_combined = query + " and " + combination.map(x => x.clause).mkString(" and \n")
		    	 if ((Utility.query_to_vector(query_combined).toSet -- original).size == 0){
		    		 return query_combined
		    	 }
		     }
		   }
		}
		query
	}
	
	def remove(xs: Array[ConditionVariable], i: Int) = (xs take i) ++ (xs drop (i + 1))
	
}







