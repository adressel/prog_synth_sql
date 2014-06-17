package src
import java.sql.DriverManager
import java.sql.Connection

object Data {

	val root = "./" // for Ian
//    val root = "/Users/Stephen/Desktop/prog_synth_sql/" // for Sheng
  
	val databaseName = "test"
	val driver = "com.mysql.jdbc.Driver"
	val url = s"jdbc:mysql://localhost:3306/$databaseName"
	val username = "root"
	val password = ""
	val table_names = Vector("user", "album")
	
	def desired_query = s"$desired_selects$desired_where"
	
	val desired_attr_names = Vector("albumname")
	val desired_tables = table_names.mkString(", ")
	val desired_selects = s"select ${desired_attr_names.mkString(", ")} from $desired_tables"
	val desired_where = " where user.username = album.owner"
	println(desired_selects + desired_where)

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	def closeConnection = connection.close
}
