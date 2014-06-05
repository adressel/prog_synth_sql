package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	AttributeVariable.populate
	ConditionVariable.populate
	OutputVariable.populate
	Clause.populate
	Printer.printFile
	CNF.solve
}