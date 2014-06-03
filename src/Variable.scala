package src
import scala.collection.mutable._

object Variable {
	type Literal = Tuple2[Variable, Boolean]
	var count = 0;
	def newId() = {count += 1; count}
	
	val all : ArrayBuffer[Variable] = ArrayBuffer()
}

class Variable {
	val id = Variable.newId
	Variable.all += this
	def print() : String = ""
}
