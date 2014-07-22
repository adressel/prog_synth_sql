package src
import java.io.File
import scala.collection.mutable.ArrayBuffer
import scala.sys.process._

// CNF is responsible for solving and processing the result from zchaff

object CNF {
  // a Array of where clauses that correspond with the zchaff output
  var wheres: Array[String] = Array()
  // the query that corresponds with zchaff output
  var query: String = ""
  // a list of variable IDs that zchaff outputs as true
  var clauses: Array[Int] = Array()
  // the zchaff runtime
  var runtime = 0.0

  def solve = {
    // run zchaff and convert it's output to a string (result)
    val result = s"${Data.root}zchaff ${Data.root}sat/cnf_files/output.cnf" !!

    // captures the zchaff output into a string (clause_list)
    val Some(clause_list) = "(.*)Random Seed Used".r.findFirstMatchIn(result)
    // populate clauses as a Array of Integers of true variables in the string clause_list
    // true variables do not have a preceding '-'
    clauses = clause_list.group(1).split(" ").filter(x => x.length > 0 && x(0) != '-').map(x => x.toInt)
    println(result)

    // if clauses is empty, then zchaff could not find a solution
    if (clauses.size == 0) {
      println("No solution found!  Printing WHERE clauses:")
      println(s"${Data.desired_selects} where ")
      ConditionVariable.all.map(_.clause).foreach(x => println(x + " and "))
    }

    // regex to find the runtime
    val Some(runtime_match) = """Total Run Time\s*(\d+.?\d*)""".r.findFirstMatchIn(result)
    runtime = runtime_match.group(1).toDouble
  }

  // measure the performance of the answer provided by zchaff
  def evaluate_correctness = {
    // "correct" output
    val original = Utility.query_to_vector(Data.desired_query).toSet
    // zchaff's query output
    val derived = Utility.query_to_vector(query).toSet

    // any in the correct output that we missed
    val missed = (original -- derived).size
    // any in our output that were not in the correct output
    val extras = (derived -- original).size

    println(s"Out of ${original.size} tuples, $missed were missed, with $extras extra")
    println(s"query : ${Data.desired_query}")
  }

  // post processing in an attempt to only return the smallest combination of clauses output by 
  // zchaff that will still give a good answer
  def post_process: String = {
    val original = Utility.query_to_vector(Data.desired_query).toSet
    val conditions = clauses.map(x => Variable.all(x - 1)).collect { case x: ConditionVariable => x }
    val wheres = conditions.map(x => x.clause)
    if (!wheres.isEmpty)
      query = s"${Data.desired_selects} where ${wheres.mkString(" and \n")}"
    else
      return "nothing in the where clauses!"
    if ((original -- Utility.query_to_vector(query).toSet).size != 0
      || (Utility.query_to_vector(query).toSet -- original).size != 0)
      return "The derived query we get is wrong! \n" + query //to check whether the derived query is right

    val crucial_clauses: ArrayBuffer[ConditionVariable] = ArrayBuffer()
    var i = 0
    for (i <- 0 until (conditions.length - 1)) {
      val test_where = remove(conditions, i).map(x => x.clause)
      val test_query = s"${Data.desired_selects} where ${test_where.mkString(" and \n")}"
      val derived = Utility.query_to_vector(test_query).toSet
      if ((derived -- original).size != 0) {
        crucial_clauses += conditions(i)
      }
    }

    val crucial_wheres = crucial_clauses.map(x => x.clause)
    query = s"${Data.desired_selects}" + (if (!crucial_wheres.isEmpty) s" where ${crucial_wheres.mkString(" and \n")}" else "")
    if ((Utility.query_to_vector(query).toSet -- original).size != 0) {
      //conditions.filter(x => !crucial_clauses.contains(x)).map(x => println(x.clause))
      for (
        combination <- conditions.filter(x => !crucial_clauses.contains(x)).
          toSet[ConditionVariable].subsets.map(_.toList).toList
      ) {
        if (!combination.isEmpty) {
          val query_combined = query + " and " + combination.map(x => x.clause).mkString(" and \n")
          if ((Utility.query_to_vector(query_combined).toSet -- original).size == 0) {
            return query_combined
          }
        }
      }
    }
    query
  }

  def remove(xs: Array[ConditionVariable], i: Int) = (xs take i) ++ (xs drop (i + 1))
}







