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
	var tableNames : Tuple2[String, String] = ("","")
	var pKeys : Tuple2[Vector[String],Vector[String]] = (Vector(),Vector())
	
	/*** Functions ***/
	def selectString : String = {
		val selectArgs = (pKeys._1 ++: pKeys._2).mkString(", ")
		s"select $selectArgs from "
	}
	
	// return a list of condition clauses given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		
		tableNames = (tableName1, tableName2)
		
		pKeys = (Utility.getPrimaryKeys(connection, tableName1),
				Utility.getPrimaryKeys(connection, tableName2))
		
		val OutputVariables : ArrayBuffer[OutputVariable] = ArrayBuffer()
		
		val resultSet1 = Utility.selectAllColumns(connection, tableName1, pKeys._1)
		while(resultSet1.next()) {
			val resultSet2 = Utility.selectAllColumns(connection, tableName2, pKeys._2)
			while(resultSet2.next()) {
				OutputVariables += new OutputVariable(Utility.resultToVector(resultSet1), 
						Utility.resultToVector(resultSet2))
			}
		}
		
		OutputVariables.toVector
	}
}