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
//		ConditionVariable.populate_unary("!=")
//		ConditionVariable.populate_unary("<=")
//		ConditionVariable.populate_unary(">=")
		Clause.populate
		println("printing")
		Printer.print_file
	}
}