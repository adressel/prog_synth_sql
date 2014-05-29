package src

object Clause {
	private var count = 0;
	def newClauseId() = {count += 1; count}
}

class Clause {
	val id = Clause.newClauseId
	def print() {}
}
