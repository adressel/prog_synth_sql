package src
import scala.collection.mutable._

// Variable.scala is responsible for assigning every variable a unique id
// Anything that can be considered a variable to our CNF encoding should
// be a subclass of Variable to inherit it's id-tracking system

object Variable {
	type Literal = Tuple2[Int, Boolean]
	var count = 0;
	def newId() = {count += 1; count}
	
	val all : ArrayBuffer[Variable] = ArrayBuffer()
}

class Variable {
	val id = Variable.newId
	Variable.all += this
	def name = ""
	def print  = ""
}
