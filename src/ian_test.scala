package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.sys.process._

object ian_test extends App {
	
	AttributeVariable.populate(Data.tableNames)
	ConditionVariable.populate(AttributeVariable.all)
	OutputDesiredVariable.populate(Data.tableNames, Data.desiredTableName)
	OutputVariable.populate(Data.tableNames)
	Clause.populate
	Printer.printFile
	//Reader.readFile
}