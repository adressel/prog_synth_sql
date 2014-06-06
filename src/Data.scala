package src
import java.sql.DriverManager
import java.sql.Connection

object Data {
	val databaseName = "test3"
	val driver = "com.mysql.jdbc.Driver"
	val url = s"jdbc:mysql://localhost:3306/$databaseName"
	val username = "root"
	val password = "123456"
	val desiredTableName = "desiredoutput"
	val tableNames = Vector("item", "stock")
	val desired_query = s"select item.I_ID from ${tableNames.mkString(", ")}"
	val desired_query_where = " where item.I_ID < 200000"
		

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	def closeConnection = connection.close
	
}