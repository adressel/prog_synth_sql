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
	
	def all = otvs
	def getKey = keys
	
	def populate = {
		otvs = Utility.query_to_vector(Data.desired_query + Data.desired_where)
				.map(x => new OutputVariable(x))
	}
}