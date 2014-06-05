package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import java.io.File
import java.io.InputStreamReader
import scala.sys.process._

object ian_test extends App {
	AttributeVariable.populate(Data.tableNames)
	ConditionVariable.populate(AttributeVariable.all)
	OutputVariable.populate
	Clause.populate
	Printer.printFile
	val p = Process("zchaff cnf_files/output.cnf", new File("./sat"))
	println(p !!)
//	Reader.printQueryFromResult
}