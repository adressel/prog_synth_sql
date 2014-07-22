package src
import scala.io.Source
import java.io._
import scala.io._
import scala.collection.mutable.ArrayBuffer

// File.scala contains objects that are responsible for reading/writing zchaff input/output


// Printer prints all the Clauses in Clause.scala to a designated file.
object Printer {
	
	def print_file = {
		// outputfile directory using Data.root 
    val output_dir = new File(s"${Data.root}sat/cnf_files/output.cnf")
    // create the BufferedWriter
    val out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream( output_dir) ), 8000000 )

    //print the header of the .cnf file
		val header = s"c output.enc\nc\np cnf ${Variable.count} ${Clause.size} \n"
		out.write(header)

		// print out every clause
		for (clause <- Clause.clauses){
	 	  val ruleNum : Int = clause._2 
	 	  // add a comment indicating which rule# this clause corresponds to (for debugging)
		  out.write(s"c =========  rule $ruleNum  ============\n")

		  // print out every clause associated with the rule
		  for (rules <- clause._1){
	      out.write(rules.literals)
			  out.write(" 0\n")
		  } 

	    out.flush()
		}
		out.close()
	}
}
