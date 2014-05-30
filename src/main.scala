package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {

	AttributeVariable.populate(Data.connection, Vector("album", "contain"))
	ConditionVariable.populate(Data.connection, AttributeVariable.all)
	OutputVariable.populate(Data.connection, Vector("album", "contain"))
	
}