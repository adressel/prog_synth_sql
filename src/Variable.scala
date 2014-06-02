package src

object Variable {
	type Literal = Tuple2[Variable, Boolean]
	var count = 0;
	def newId() = {count += 1; count}
}

class Variable {
	val id = Variable.newId
	def print() {}
}
