package src
import scala.collection.mutable._
import java.sql.Connection

class OutputVariable (
	key1: Vector[Any],
	key2: Vector[Any]
) extends Variable {
	val matches : ArrayBuffer[ConditionVariable] = ArrayBuffer()
	override def print() = {
		println(s"id: $id key1:${key1.mkString(", ")} key2: ${key2.mkString(", ")}")
	}
	override def toString() = s"id: $id key1:${key1.mkString(", ")} key2: ${key2.mkString(", ")}"

	def keyVector = key1 ++ key2
}

object OutputVariable {
	var tableNames : Vector[String] = Vector()
	var pKeys : Vector[Vector[String]] = Vector()
	
	private var otvs : Vector[OutputVariable] = Vector()
	
	def all = otvs
	
	
	/*** Functions ***/
	def selectQuery : String = {
		val selectArgs = pKeys(0).map(_+tableNames(0)) ++: pKeys(1).map(_+tableNames(1))
		s"select ${selectArgs.mkString(", ")} from ${tableNames.mkString(", ")} "
	}
	
	// return a Vector of condition clauses given two tables
	def populate(tableNameVec : Vector[String])  = {
		
		tableNames = tableNameVec
		
		pKeys = Vector(Utility.getPrimaryKeys(tableNames(0)),
				Utility.getPrimaryKeys(tableNames(1)))
		
		val OutputVariables : ArrayBuffer[OutputVariable] = ArrayBuffer()
		
		val resultSet1 = Utility.selectAllColumns(tableNames(0), pKeys(0))
		while(resultSet1.next()) {
			val resultSet2 = Utility.selectAllColumns(tableNames(1), pKeys(1))
			while(resultSet2.next()) {
				OutputVariables += new OutputVariable(Utility.resultToVector(resultSet1), 
						Utility.resultToVector(resultSet2))
			}
		}
		otvs = OutputVariables.toVector
	}
}