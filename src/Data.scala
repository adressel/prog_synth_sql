package src
import java.sql.DriverManager
import java.sql.Connection

object Data {
	val databaseName = "test"
	val driver = "com.mysql.jdbc.Driver"
	val url = "jdbc:mysql://localhost:3306/" + databaseName
	val username = "root"
	val password = ""
	val desiredTableName = "desiredoutput"
	val desired_query = "select album.albumid, usr.username from album, usr"
	val desired_query_where = " where album.username = usr.username"
		
	val tableNames = Vector("album", "usr")

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	def closeConnection = connection.close
	
}