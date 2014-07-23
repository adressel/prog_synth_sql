package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

// Skim over Main.scala.

// read Data.scala and Utility.scala and Variable.scala before reading other files
// Then, you can read the files in the order they appear in main.

object main extends App {

	// encoding
	val encode_time = Utility.time {
		() =>
		OutputVariable.populate
		AttributeVariable.populate
		ConditionVariable.populate_binary("=")
		ConditionVariable.populate_unary_max_min
		// uncomment the lines below to add extra condition variables
//		ConditionVariable.populate_unary("!=", Vector("timestamp"))
//		ConditionVariable.all.map(x => println(x.query))
		Clause.populate
		println("printing")
	}
	
	val print_time = Utility.time{
	  () => Printer.print_file
	}
	
	val solve_time = Utility.time {
		() =>
		println("solving")
		CNF.solve
	} 
	
	var process_time = 0.0
	if(CNF.clauses.size > 0) {
		println("processing")
		process_time = Utility.time {
			CNF.post_process _
		}
		println(s"query: ${CNF.query}")

		CNF.evaluate_correctness
	}
	// print runtime and other metrics
	println("print time : " + print_time)
	println(s"encode time: $encode_time")
	println(s"solve_time: $solve_time")
	println(s"process_time: $process_time")
	println("")
	println(s"variables: ${Variable.all.size}")
	println(s"clauses: ${Clause.size}")
	println(s"queries: ${Utility.query_count}")
	println("")
	println(s"unary clauses: ${ConditionVariable.get_unary.size}")
	println(s"binary clauses: ${ConditionVariable.get_binary.size}")

}

