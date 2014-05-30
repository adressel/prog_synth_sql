package src
import scala.collection.mutable._
import java.sql.Connection

class OutputVariable (
	key1 : Vector[Any], 
	key2: Vector[Any]
) extends Variable {
	override def print() = {
		println(s"id: $id key1:${key1.mkString(", ")} key2: ${key2.mkString(", ")}")
	}
}

object OutputVariable {
	var tableNames : List[String] = List()
	var pKeys : List[Vector[String]] = List()
	
	/*** Functions ***/
	def selectQuery : String = {
		val selectArgs = pKeys(0).map(_+tableNames(0)) ++: pKeys(1).map(_+tableNames(1))
		s"select ${selectArgs.mkString(", ")} from ${tableNames.mkString(", ")} "
	}
	
	// return a list of condition clauses given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		
		tableNames = List(tableName1, tableName2)
		
		pKeys = List(Utility.getPrimaryKeys(connection, tableName1),
				Utility.getPrimaryKeys(connection, tableName2))
		
		val OutputVariables : ArrayBuffer[OutputVariable] = ArrayBuffer()
		
		val resultSet1 = Utility.selectAllColumns(connection, tableName1, pKeys(0))
		while(resultSet1.next()) {
			val resultSet2 = Utility.selectAllColumns(connection, tableName2, pKeys(1))
			while(resultSet2.next()) {
				OutputVariables += new OutputVariable(Utility.resultToVector(resultSet1), 
						Utility.resultToVector(resultSet2))
			}
		}
		
		OutputVariables.toVector
	}
}