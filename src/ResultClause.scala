package src
import java.sql.Connection

class ResultClause (row_id1: Int, row_id2 : Int) extends Clause{
	
}

object ResultClause {
	// return a list of condition clauses given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		
	}
}