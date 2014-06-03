package src
import scala.collection.mutable._
import java.sql.Connection

class OutputVariable (
	val key1: Vector[Any]
) extends Variable {
	val matches : ArrayBuffer[ConditionVariable] = ArrayBuffer()
	override def print() = {
		s"id: $id key1:${key1.mkString(", ")} "
	}
	override def toString() = s"id: $id key1:${key1.mkString(", ")}"

	def keyVector = key1
}

object OutputVariable {
	var tableNames : Vector[String] = Vector()
	var keys : Vector[String] = Vector()
	private var otvs : Vector[OutputVariable] = Vector()
	
	def all = otvs
	def getKey = keys
	
	/*** Functions ***/
	def selectQuery : String = {
		s"select ${keys.mkString(", ")} from ${tableNames.mkString(", ")} where "
	}
	
	// return a Vector of condition clauses given two tables
	def populate(tableNameVec : Vector[String])  = {
		
		tableNames = tableNameVec
		keys = for (attr <- OutputDesiredVariable.all) yield {
			attr.tableName + "." + attr.attrName // Temporarily using
		}
	
		val OutputVariables : ArrayBuffer[OutputVariable] = ArrayBuffer()
		val results = Utility.queryToVector(selectQuery + "true")
		for(result <- results)
		  OutputVariables += new OutputVariable(result) 
		
		otvs = OutputVariables.toVector
	}
}