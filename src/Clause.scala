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
		rule1234
	}
	
	def rule1234 {
		val clause_buffer_2 : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
		val clause_buffer_3 : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
		val clause_buffer_4 : mutable.ArrayBuffer[Clause] = mutable.ArrayBuffer()
		
	
		
		// RULE 2
		val desired_otvs = OutputVariable.good.map(x => x._1).toSet
		val bad_otvs = OutputVariable.bad
		for(otv <- bad_otvs)
			clause_buffer_2 += new Clause(Vector((otv._2, false)))

		
		// bad_otv_cv_map maps query tuples to Tuple2[Int, mutable.Set[Int] ]
		// which contains the otv id, and the cv id that maps to it.
		val otv_cv_map = OutputVariable.all.map(x => (x._1, (x._2, mutable.Set[Int]() )))
		for(cv <- ConditionVariable.all) {
			val result_set = Utility.query_to_vector(cv.query).toSet
			for(otv <- OutputVariable.all -- result_set) {
				otv_cv_map(otv._1)._2 += cv.id
				// RULE 4
				clause_buffer_4 += new Clause(Vector((cv.id, false), (otv._2, false)))
			}
		}
		
		// RULE 3
		for(otv <- otv_cv_map -- desired_otvs) {
			val cv_literals = otv._2._2.map(x => (x, true))
			clause_buffer_3 += new Clause(Vector((otv._2._1, true)) ++ cv_literals)
		}
		
		
		Clause.clauses += ((clause_buffer_2, 2))
		Clause.clauses += ((clause_buffer_3, 3))
		Clause.clauses += ((clause_buffer_4, 4))
	}
	
}