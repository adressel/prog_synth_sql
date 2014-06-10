package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	
	val encode_time = Utility.time {
		() =>
		OutputVariable.populate
		AttributeVariable.populate
		ConditionVariable.populate_binary("=")
		ConditionVariable.populate_unary_max_min
		ConditionVariable.populate_unary("!=")
		ConditionVariable.populate_unary("<=")
		ConditionVariable.populate_unary(">=")
		Clause.populate
		println("printing")
//		Printer.print_file
	}
	println(ConditionVariable.all.size)
	println(Variable.all.size)
	println(Clause.size)
//	println("solving")
//	val solve_time = Utility.time {
//		CNF.solve _
//	}
	
//	val process_time = Utility.time {
//		CNF.post_process _
//	}
	
	println(s"encode time: $encode_time")
//	println(s"solve_time: $solve_time")
//	println(s"process_time: $process_time")
	
//	println(CNF.query)
//	CNF.evaluate_correctness
}