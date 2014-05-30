package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {
	val databaseName = "test"
	 val driver = "com.mysql.jdbc.Driver"
	val url = "jdbc:mysql://localhost:3306/" + databaseName
	val username = "root"
	val password = "123456"

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	val tableNames : MutableList[String] = MutableList()
	 val statement = connection.createStatement();
	 val  resultSet = statement.executeQuery("SHOW tables;")
	 while (resultSet.next())
	 {
	   tableNames += resultSet.getString("Tables_in_" + databaseName)
	 }

	val x = AttributeClause.populate(connection, tableNames.toList)
	println(x)
	
	val y = ConditionClause.populate(connection, x.toList)
	
	println(y)
	AttributeClause.test
	
}