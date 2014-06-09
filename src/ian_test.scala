package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	OutputVariable.populate
	AttributeVariable.populate
	ConditionVariable.populate_binary("=")
	Clause.populate
	Printer.print_file
//	OutputVariable.get_types
}