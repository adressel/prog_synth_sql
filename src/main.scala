package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {
	AttributeVariable.populate
	ConditionVariable.populate
	OutputVariable.populate
	Clause2.populate
	Printer.printFile
	CNF.solve
}

