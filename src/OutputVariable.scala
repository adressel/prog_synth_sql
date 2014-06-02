package src
import scala.collection.mutable._
import java.sql.Connection

class OutputVariable (
	val key1: Vector[Any]
) extends Variable {
	val matches : ArrayBuffer[ConditionVariable] = ArrayBuffer()
	override def print() = {
		println(s"id: $id key1:${key1.mkString(", ")} ")
	}
	override def toString() = s"id: $id key1:${key1.mkString(", ")}"

	def keyVector = key1
}

object OutputVariable {
	var tableNames : Vector[String] = Vector()
	var pKeys : Vector[Vector[String]] = Vector()
	var keys : Vector[String] = Vector()
	private var otvs : Vector[OutputVariable] = Vector()
	
	def all = otvs
	def getKey = keys
	
	/*** Functions ***/
	def selectQuery : String = {
		val selectArgs = pKeys(0).map(tableNames(0)+"."+_) ++: pKeys(1).map(tableNames(1)+"."+_)
		s"select ${selectArgs.mkString(", ")} from ${tableNames.mkString(", ")} where "
	}
	
	// return a Vector of condition clauses given two tables
	def populate(tableNameVec : Vector[String])  = {
		
		tableNames = tableNameVec
		//val temp = OutputDesiredVariable.all
		//pKeys = Vector(Utility.getPrimaryKeys(tableNames(0)),
		//		Utility.getPrimaryKeys(tableNames(1)))
		keys = for (attr <- OutputDesiredVariable.all) yield{
			attr.tableName + "." + attr.attrName // Temporarily using
		}
		val OutputVariables : ArrayBuffer[OutputVariable] = ArrayBuffer()
		
//		val resultSet1 = Utility.selectAllColumns(tableNames(0), pKeys(0))
//		while(resultSet1.next()) {
//			val resultSet2 = Utility.selectAllColumns(tableNames(1), pKeys(1))
//			while(resultSet2.next()) {
//				OutputVariables += new OutputVariable(Utility.resultToVector(resultSet1), 
//						Utility.resultToVector(resultSet2))
//			}
//		}
		val resultSet1 = Utility.selectAllColumns(tableNames, keys.toVector)
		while(resultSet1.next()) {
		  //println(Utility.resultToVector(resultSet1))
		  OutputVariables += new OutputVariable(Utility.resultToVector(resultSet1)) 
		}
		otvs = OutputVariables.toVector
	}
}