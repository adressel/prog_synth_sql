
package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object test extends App {
	val driver = "com.mysql.jdbc.Driver"
	val url = "jdbc:mysql://localhost:3306/test"
	val username = "root"
	val password = ""
	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
//	val statement = connection.createStatement()
//	val resultSet = statement.executeQuery("SELECT * FROM album")
//	while(resultSet.next()) {
//		println(resultSet.getString("username"))
//	}
	
	Utility.getPrimaryKeys(connection, "contain")
	
	connection.close()
}