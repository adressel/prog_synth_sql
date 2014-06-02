package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	
	AttributeVariable.populate(Vector("album", "contain"))
	ConditionVariable.populate(AttributeVariable.all)
	OutputDesiredVariable.populate(Vector("album", "contain"), "OutputAtoC")
	OutputVariable.populate(Vector("album", "contain"))
	Clause.rule4and5
	
}