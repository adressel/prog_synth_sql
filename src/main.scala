package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {
	
	val encode_time = Utility.time {
		() =>
		OutputVariable.populate
		AttributeVariable.populate
		ConditionVariable.populate_binary("=")
		ConditionVariable.populate_unary_max_min
//		ConditionVariable.populate_unary("!=", Vector("timestamp"))
//		ConditionVariable.all.map(x => println(x.query))
		Clause.populate
	}
	val print_time = Utility.time {
	  () =>
	  println("printing")
	  Printer.print_file
	}
	val encoder_memory = Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory
	println("solving")
	CNF.solve 
	
	var process_time = 0
	if(CNF.clauses.size > 0) {
		println("processing")
		val process_time = Utility.time {
			CNF.post_process _
		}
		println(s"query: ${CNF.query}\n")

		CNF.evaluate_correctness
	}
	println(s"encode time: $encode_time")
	println(s"print_time: $print_time")
	println(s"solve_time: ${CNF.runtime}")
	println(s"process_time: $process_time")
	println(s"encoder_memory: $encoder_memory")
	println(s"query_time: ${Utility.query_time}")
	println("")
	println(s"variables: ${Variable.all.size}")
	println(s"clauses: ${Clause.size}")
	println(s"queries: ${Utility.query_count}")
	println("")
	println(s"unary clauses: ${ConditionVariable.get_unary.size}")
	println(s"binary clauses: ${ConditionVariable.get_binary.size}")
	
	
}

