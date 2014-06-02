package src
import java.sql.DriverManager
import java.sql.Connection

object Data {
	val databaseName = "test"
	val driver = "com.mysql.jdbc.Driver"
	val url = "jdbc:mysql://localhost:3306/" + databaseName
	val username = "root"
	val password = "123456"
	val desiredTableName = "OutputAtoC"

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	def closeConnection = connection.close
}