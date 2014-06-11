package src
import java.sql.DriverManager
import java.sql.Connection

object Data {

//	val root = "./" // for Ian
    val root = "/Users/Stephen/Desktop/prog_synth_sql/" // for Sheng
  
	val databaseName = "test3"
	val driver = "com.mysql.jdbc.Driver"
	val url = s"jdbc:mysql://localhost:3306/$databaseName"
	val username = "root"
	val password = "123456"
	val table_names = Vector("item", "history")
	
	def desired_query = s"$desired_selects$desired_where"
	
	val desired_attr_names = Vector("I_DATA", "I_NAME","H_DATA", "H_C_ID")
	val desired_tables = table_names.mkString(", ")
	val desired_selects = s"select ${desired_attr_names.mkString(", ")} from $desired_tables"
	val desired_where = " where H_C_ID = I_IM_ID and I_ID > 99000"
	println(desired_selects + desired_where)

	Class.forName(driver)
	val connection = DriverManager.getConnection(url, username, password)
	if(connection == null) println("failed")
	
	def closeConnection = connection.close
	
//	def prepareDatabase (num : Int) = {
//	  val result1 = s"mysql -u${Data.username} -p${Data.password} test <${Data.root}/sql/tbl_create.sql" !!
//	  val result2 = s"mysql -u${Data.username} -p${Data.password} test <${Data.root}/sql/load_data.sql$num" !!
//	}
	
}

class Data {
	
}