package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	OutputVariable.populate
	AttributeVariable.populate
	println(AttributeVariable.all)
	ConditionVariable.populate_binary("=")
	Clause.populate
	Printer.print_file
	CNF.solve
	CNF.post_process
	
	println(CNF.query)
//	OutputVariable.get_types
}