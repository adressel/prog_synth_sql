package src
import scala.collection.mutable._
import java.sql.Connection

class ResultClause (key1 : ArrayBuffer[Any], key2: ArrayBuffer[Any]) extends Clause{
	override def print() = {
		println(s"id: $id key1:${key1.mkString(", ")} key2: ${key2.mkString(", ")}")
	}
}



object ResultClause {
	
	// return a list of condition clauses given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		val pkeys1 = Utility.getPrimaryKeys(connection, tableName1)
		val pkeys2 = Utility.getPrimaryKeys(connection, tableName2)
		
		println(pkeys1.mkString(", "))
		
		val resultClauses : List[ResultClause] = List()
		
		val resultSet1 = Utility.selectAllColumns(connection, tableName1, pkeys1)
		while(resultSet1.next()) {
			val resultSet2 = Utility.selectAllColumns(connection, tableName2, pkeys2)
			while(resultSet2.next()) {
				resultClauses :+ new ResultClause(Utility.resultToList(resultSet1), 
						Utility.resultToList(resultSet2))
			}
		}
		
		resultClauses
	}
}