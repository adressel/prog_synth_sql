package src
import java.sql.Connection

class ResultClause (key1 : List[Any], key2: List[Any]) extends Clause{
	
}



object ResultClause {
	
	// return a list of condition clauses given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		val pkeys1 = Utility.getPrimaryKeys(connection, tableName1)
		val pkeys2 = Utility.getPrimaryKeys(connection, tableName2)
		
		val resultClauses : List[ResultClause] = List()
		
		val resultSet1 = Utility.selectAllColumns(connection, tableName1, pkeys1)
		while(resultSet1.next()) {
			val resultSet2 = Utility.selectAllColumns(connection, tableName2, pkeys2)
			while(resultSet2.next()) {
				
			}
		}
	}
}