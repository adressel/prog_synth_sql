package src
import scala.collection.mutable._

// Variable.scala is responsible for assigning every variable a unique id
// Anything that can be considered a variable to our CNF encoding should
// be a subclass of Variable to inherit it's id-tracking system

object Variable {
	type Literal = Tuple2[Int, Boolean]    // define a literal type
	var count = 0;                         // a variable id tracker
	def newId() = {count += 1; count}      // give a new variable id to the variable constructor
	
	val all : ArrayBuffer[Variable] = ArrayBuffer()
}

class Variable {
	val id = Variable.newId  // a unique id for zchaff .cnf files
	Variable.all += this     // add this new variable to the variable master-list
	def name = ""            
	def print  = ""
}
