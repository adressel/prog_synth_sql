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
		println("printing")
		Printer.print_file
	}
	println("solving")
	val solve_time = Utility.time {
		CNF.solve _
	}
	println("expected")
	println(CNF.post_process) 
	println("expected")
//	val process_time = Utility.time {
//		CNF.post_process _
//	}
//	
//	val encoder_memory = Runtime.getRuntime.totalMemory - Runtime.getRuntime.freeMemory
//	
//	println(s"query: ${CNF.query}\n")
//
//	CNF.evaluate_correctness
//	
//	println(s"encode time: $encode_time")
//	println(s"solve_time: $solve_time")
//	println(s"process_time: $process_time")
//	println(s"encoder_memory: $encoder_memory")
//	println("")
//	println(s"variables: ${Variable.all.size}")
//	println(s"clauses: ${Clause.size}")
//	println(s"queries: ${Utility.query_count}")
//	println("")
//	println(s"unary clauses: ${ConditionVariable.get_unary.size}")
//	println(s"binary clauses: ${ConditionVariable.get_binary.size}")
}

