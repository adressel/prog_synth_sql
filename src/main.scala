package src
import java.sql.DriverManager
import java.sql.Connection
import com.mysql.jdbc
import scala.collection.mutable.MutableList

object main extends App {
	OutputVariable.populate
	AttributeVariable.populate
	ConditionVariable.populate_binary("=")
//	println(ConditionVariable.all.map(x => x.query + "\n"))
	Clause.populate
	Printer.print_file
	println("solve cnf")
	CNF.solve
	println("done cnf")
	CNF.post_process
	CNF.evaluate_correctness
//	println(OutputVariable.get_otv.mkString(", "))
//	val otvs_pop_time = Utility.time(OutputVariable.populate _)
//	val rule_pop_time = Utility.time(Clause.populate _)
//	val print_time = Utility.time(Printer.printFile _)
//	val solve_time = Utility.time(CNF.solve _)
//	val process_time = Utility.time(CNF.post_process _)

	
////	val encode_time = attr_pop_time + cond_pop_time + otvs_pop_time + rule_pop_time
//	println("########### TIME PROFILE #############")
////	println(f"encode_time   : $encode_time%2.3f")
//	println(f"print_time    : $print_time%2.3f")
//	println(f"solve_time    : $solve_time%2.3f")
//	println(f"process_time  : $process_time%2.3f")
//	println("########### OTHER STATS #############")
//	println(s"# variables   : ${Variable.all.size}")
//	println(s"# sat_clauses : ${Clause.size}")
//	println(s"# queries     : ${Utility.total_queries}")
//	println(s"# qry_clauses : ${CNF.wheres.size}")
//	println("########### CORRECTNESS #############")
//	CNF.evaluate_correctness
//	
//	println("")
//	println(CNF.query)
//	Clause.printQuery
}

