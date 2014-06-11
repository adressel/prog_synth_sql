package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc

object ian_test extends App {
	
	val encode_time = Utility.time {
		() =>
		OutputVariable.populate
		
	}
	for(otv <- OutputVariable.all) {
		println(otv.tuple)
	}
}