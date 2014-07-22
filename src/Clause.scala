package src
import scala.collection.mutable.ArrayBuffer
import scala.collection._

// Clause.scala is responsible for creating the rules and converting them into an easily-printable form.


// A clause is just a wrapper for a String.  
class Clause (
	val literals : String
) {}

object Clause {
	
	// a ArrayBuffer of Clauses paired with the rule number associated with it.  
	// For example, there might be 10 Clauses associated with the Integer 1 to show
	// that these 10 clauses correspond to Rule #1
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()

	def populate = {
		rule1234
	}
	
	// creates clauses for every rule
	def rule1234 {
		val clause_buffer_3 : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
		val clause_buffer_4 : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
		
		// a set of desired otvs
		val desired_otvs = OutputVariable.good.map(x => x._1).toSet
		
		// bad_otv_cv_map maps query tuples to Tuple2[Int, mutable.Set[Int] ]
		// which contains the otv id, and the cv id that maps to it.
		val otv_cv_map = OutputVariable.all.map(x => (x._1, (x._2, mutable.Set[Int]() )))

		// iterate through every condition variable, and 
		for(cv <- ConditionVariable.all) {
			// Utility.query_to_vector converts a query to an vector of vectors representing
			// the results of the query.  So, Utility.query_to_vector(cv.query) returns the
			// results of the condition variable cv
			val result_set = Utility.query_to_vector(cv.query).toSet

			// iterate through all good otvs not included by this cv
			for(otv <- OutputVariable.good -- result_set) {
				// show that this cv implies each of these otvs
				clause_buffer_4 += new Clause(s"-${cv.id} ${otv._2}")
			}

			// iterate through all bad otvs NOT excluded by the current cv
			for(otv <- OutputVariable.bad -- result_set) {
				// update otv_cv_map to indicate that this undesired otv is NOT excluded by
				// the current cv
				otv_cv_map(otv._1)._2 += cv.id
			}
		}
		
		// RULE 3
		// iterate through every unwanted otv, and convert the list of cv_ids to a string
		for(otv <- otv_cv_map -- desired_otvs) {
			if(otv._2._2.size > 0) 
			  clause_buffer_3 += new Clause(otv._2._2.mkString(" "))
		}
		
		Clause.clauses += ((clause_buffer_3, 3))
		Clause.clauses += ((clause_buffer_4, 4))
	}

	// a helper function to give the total # of clauses encoded
	def size = clauses.map(x => x._1.size).sum
	
}