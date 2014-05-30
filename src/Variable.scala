package src

object Variable {
	private var count = 0;
	def newId() = {count += 1; count}
}

class Variable {
	val id = Variable.newId
	def print() {}
}
