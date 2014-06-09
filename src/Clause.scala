package src
import scala.collection.mutable._
class Clause (
	//Variable.Literal is just a Tuple2[Variable, Boolean]
	// val literals: Vector[Tuple2[Int, Boolean]]
	val literals : Vector[Variable.Literal]
) {
}

object Clause {
	val max_clause_length = 4
	
	def size = clauses.map(x => x._1.size).sum
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()

	def populate = {
		rule2
		rule3
	}
	
	def rule2 {
		val otv_cv_map = OutputVariable.all.map(x => (x.tuple, (x, Set[ConditionVariable]()))).toMap
		for(cv <- ConditionVariable.all) {
			val result_set = Utility.query_to_vector(cv.query)
			for(result <- result_set) {
				otv_cv_map(result)._2 += cv
			}
		}
		
		val clause_buffer : ArrayBuffer[Clause] = ArrayBuffer()
		for ((_, otv_pair) <- otv_cv_map) {
			val rule_vector = Vector((otv_pair._1, false)) ++ otv_pair._2.map(x => (x, true)).toVector
			clause_buffer += new Clause(rule_vector)
		}
		
		clauses += ((clause_buffer, 2))
	}
	
	def rule3 {
		val clause_buffer : ArrayBuffer[Clause] = ArrayBuffer()
		
		val cv_set = ConditionVariable.all.toSet
		val combinations_seq_seq = for(i <- 1 to max_clause_length) yield 
			cv_set.subsets(i).toIndexedSeq
		// a sequence of all combinations of queries with < max_clause_length clauses
		val combinations_seq = combinations_seq_seq.flatten
		
		// otv_tuple_set and otv_map are for otv lookup
		val otv_tuple_set = OutputVariable.all.map(x => x.tuple)
		val otv_map = OutputVariable.all.map(x => (x.tuple, x)).toMap
		
		for(combination <- combinations_seq) {
			val result_sets = for(clause <- combination) yield 
								Utility.query_to_vector(clause.query).toSet
			val intersection = result_sets.reduceLeft(_&_)
			
			val query_clauses = combination.map(x => (x, true)).toVector
			val other_clauses = (cv_set -- combination).map(x => (x, false)).toVector
			val rule_prefix = query_clauses ++ other_clauses
			
			//if intersection contains any non-otv tuples, it's a bad query
			if((intersection -- otv_tuple_set).size > 0)
				clause_buffer += new Clause(rule_prefix)
			
			// this is a good query, fill in all of the results.
			// generate the rule using the prefix and all of the otvs
			else {
				val otv_clause_set = otv_tuple_set.map(x => (otv_map(x), intersection.contains(x)))
				for(otv_clause <- otv_clause_set) 
					clause_buffer += new Clause(rule_prefix :+ otv_clause)
			} // end else
		} // end for combination
		
		clauses += ((clause_buffer, 3))
	}
}