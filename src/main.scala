package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {

	AttributeVariable.populate(Vector("album", "contain"))
	ConditionVariable.populate(AttributeVariable.all)
	OutputVariable.populate(Vector("album", "contain"))
	
}