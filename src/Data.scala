package src
import java.sql.DriverManager
import java.sql.Connection

// The Data object contains all of the configurations for running the program.  
// Also responsible for initializing the connection to the local database

object Data {

	val root = "./" // for Ian
//    val root = "/Users/Stephen/Desktop/prog_synth_sql/" // for Sheng
  
  // database information
	val databaseName = "test"
	val driver = "com.mysql.jdbc.Driver"
	val url = s"jdbc:mysql://localhost:3306/$databaseName"
	val username = "root"
	val password = ""

  // info specific to this query's runtime.  You can change the variables here to configure the
  // program to encode a different rule set.

	val table_names = Vector("user", "album")
	// a vector of input table names 
	
	val desired_attr_names = Vector("albumname")
	// a vector of desired input tables

	val desired_tables = table_names.mkString(", ")
	// a simple string containing all the input tables seperated by a comma

	val desired_selects = s"select ${desired_attr_names.mkString(", ")} from $desired_tables"
	// a desired-query prefix created from desired_attr_names and desired_tables

	val desired_where = " where user.username = album.owner"
	// the desired selection criteria.  Can be as complicated as you want

	Class.forName(driver)
	// create the connection with the database using Java's DriverManager class
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	// close the connection
	def closeConnection = connection.close

	// a helper function to get the query
	def desired_query = s"$desired_selects$desired_where"
}
