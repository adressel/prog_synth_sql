package src
import scala.collection.mutable._
import java.sql.Connection

class OutputVariable (
	val key: Vector[Any]
) extends Variable {
	val matches : ArrayBuffer[ConditionVariable] = ArrayBuffer()
	
	override def print() = {
		s"id: $id key1:${key.mkString(", ")} "
	}

	def keyVector = key
}

object OutputVariable {
	var keys : Vector[String] = Vector()
	private var otvs : Vector[OutputVariable] = Vector()
	private var expanded_table : Vector[Vector[Any]] = Vector()
	
	def table = expanded_table
	
	def all = otvs
	def getKey = keys
	
	def populate = {
		expanded_table = Utility.query_to_vector(Data.desired_query)
		otvs = Utility.query_to_vector(Data.desired_query).map(x => new OutputVariable(x))
	}
}