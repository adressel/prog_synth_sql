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
		Clause.populate
		Printer.print_file
	}
	
	val solve_time = Utility.time {
		CNF.solve _
	}
	
	val process_time = Utility.time {
		CNF.post_process _
	}
	
	println(CNF.query)
	
	println(s"encode time: $encode_time")
	println(s"solve_time: $solve_time")
	println(s"process_time: $process_time")
}