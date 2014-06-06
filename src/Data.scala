package src
import java.sql.DriverManager
import java.sql.Connection

object Data {

	//	val root = "./sat/" // for Ian
    val root = "/Users/Stephen/Desktop/prog_synth_sql/sat/" // for Sheng
  
	val databaseName = "test3"
	val driver = "com.mysql.jdbc.Driver"
	val url = s"jdbc:mysql://localhost:3306/$databaseName"
	val username = "root"
	val password = ""
	val desiredTableName = "desiredoutput"
	val tableNames = Vector("album", "usr")
	val desired_query = s"select usr.username, album.albumid from ${tableNames.mkString(", ")}"
	val desired_query_where = " where usr.username = \"spacejunkie\""
		

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	def closeConnection = connection.close
	
}