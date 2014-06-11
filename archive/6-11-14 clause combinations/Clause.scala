package src
import scala.collection.mutable.ArrayBuffer
import scala.collection._
class Clause (
	//Variable.Literal is just a Tuple2[Int, Boolean]
	val literals : Vector[Variable.Literal]
) {
}

object Clause {
	def size = clauses.map(x => x._1.size).sum
	
	val clauses : ArrayBuffer[Tuple2[ArrayBuffer[Clause], Int]] = ArrayBuffer()

	def populate = {
		rule2
		rule3
	}
	
	def rule2 {
		val otv_cv_map = OutputVariable.all.map(x => (x.tuple, (x, mutable.Set[Int]()))).toMap
		for(cv <- ConditionVariable.all) {
			val result_set = Utility.query_to_vector(cv.query).filter(otv_cv_map.contains(_))
			for(result <- result_set) {
				otv_cv_map(result)._2 += cv.id
			}
		}
		
		val clause_buffer : ArrayBuffer[Clause] = ArrayBuffer()
		for ((_, otv_pair) <- otv_cv_map) {
			val rule_vector = Vector((otv_pair._1.id, false)) ++ otv_pair._2.map(x => (x, true)).toVector
			clause_buffer += new Clause(rule_vector)
		}
		
		clauses += ((clause_buffer, 2))
	}
	
	def cv_tuples_pair(cv : ConditionVariable) = {
		(cv.id, Utility.query_to_vector(cv.query).toSet)
	}
	
	def make_prefix(combination : Set[ConditionVariable], cv_set : Set[ConditionVariable]) = {
		val query_clauses = combination.map(x => (x.id, false)).toVector
		val other_clauses = (ConditionVariable.all.toSet -- combination).
							map(x => (x.id, true)).toVector
		query_clauses ++ other_clauses
	}
	
	val max_clause_length = 4
	val max_binary_clause = 2
	val max_unary_clause = 2
	
	def combination_legal(combination : Set[ConditionVariable]) = {
		val binary_clause_count = combination.filter(_.isInstanceOf[BinaryCondition]).size
		val unary_clause_count = combination.filter(_.isInstanceOf[UnaryCondition]).size
		unary_clause_count <= max_unary_clause && binary_clause_count <= max_binary_clause
	}
	
	def legal_combinations(cv_set : Set[ConditionVariable]) = {
		val clause_buffer : ArrayBuffer[Clause] = ArrayBuffer()
		
		val legal_combinations = (1 to max_clause_length).
			        map(i => cv_set.subsets(i).toIndexedSeq).
			        flatten.
			        filter(combination_legal(_)).
			        toSet
		val all_combinations = cv_set.subsets
		
		for(combination <- all_combinations) {
			if(!legal_combinations.contains(combination))
				clause_buffer += new Clause(make_prefix(combination, cv_set))
		}
		clauses += ((clause_buffer, 4))
		legal_combinations
	}
	
	def rule3 {
		val clause_buffer : ArrayBuffer[Clause] = ArrayBuffer()
		
		val cv_set = ConditionVariable.all.toSet
		
		val cv_tuples_map = ConditionVariable.all.map(cv_tuples_pair).toMap
		
		// a sequence of all combinations of queries with < max_clause_length clauses
		val combinations = legal_combinations(cv_set)
							   
		
		// otv_tuple_set and otv_map are for otv lookup
		val otv_tuple_set = OutputVariable.all.map(x => x.tuple)
		val otv_map = OutputVariable.all.map(x => (x.tuple, x.id)).toMap
		
		for(combination <- combinations) {
			val rule_prefix = make_prefix(combination, cv_set)
			val intersection = combination.
							   map(cv => cv_tuples_map(cv.id)).
							   reduceLeft(_ intersect _)
			
			//if intersection contains any non-otv tuples, it's a bad query
			if((intersection -- otv_tuple_set).size > 0)
				clause_buffer += new Clause(rule_prefix)
			
			// this is a good query, fill in all of the results.
			// generate the rule using the prefix and all of the otvs
			else {
				val otv_clause_set = otv_tuple_set.map(x => (otv_map(x), intersection.contains(x)))
				for(otv_clause <- otv_clause_set) 
					clause_buffer += new Clause(rule_prefix :+ otv_clause)
			} 
			
		} // end for combination
		
		clauses += ((clause_buffer, 3))
	}
}