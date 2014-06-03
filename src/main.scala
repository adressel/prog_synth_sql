package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {

	AttributeVariable.populate(Vector("album", "usr"))
	ConditionVariable.populate(AttributeVariable.all)
	OutputDesiredVariable.populate(Vector("album", "usr"), Data.desiredTableName)
	OutputVariable.populate(Vector("album", "usr"))
	Clause.populate
	printer.printFile
	Reader.readFile
}