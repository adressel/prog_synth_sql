package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	val driver = "com.mysql.jdbc.Driver"
	val url = "jdbc:mysql://localhost:3306/test"
	val username = "root"
	val password = ""
	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	val rcs = ResultClause.populate(connection, "album", "usr")
	for(rc <- rcs) {
		rc.print
	}
	
	connection.close()
}