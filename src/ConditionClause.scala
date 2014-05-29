package src
import java.sql.Connection

class ConditionClause(
    left : AttributeClause, 
    op : ConditionClause.Operator.Value) 
extends Clause 
{}

class UnaryCondition(
    left : AttributeClause, 
    op : ConditionClause.Operator.Value,
    right: Any
)
extends ConditionClause(left, op)
{}

class BinaryCondition(
    left : AttributeClause, 
    op : ConditionClause.Operator.Value,
    right: AttributeClause
) extends ConditionClause(left, op) 
{}

object ConditionClause {
	object Operator extends Enumeration {
		type Operator = Value
		val Equal, NotEqual, Less, Greater, LessEq, GreatEq = Value
	}
  
	// return a list of condition clauses given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		
	}
}