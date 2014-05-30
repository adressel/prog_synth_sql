package src
import java.sql.Connection
import scala.collection.mutable.MutableList

class ConditionClause(
    left : AttributeClause, 
    op : ConditionClause.Operator.Value
) extends Variable 
{}

class UnaryCondition(
    left : AttributeClause, 
    op : ConditionClause.Operator.Value,
    right: Any
)
extends ConditionClause(left, op) {
	override def toString() = left + "  " + op + "  " + right + "\n"
}

class BinaryCondition(
    left : AttributeClause, 
    op : ConditionClause.Operator.Value,
    right: AttributeClause
) extends ConditionClause(left, op) {
	override def toString() = left + "  " + op + "  " + right + "\n"
}

object ConditionClause {
	object Operator extends Enumeration {
		type Operator = Value
		val Equal, NotEqual, Less, Greater, LessEq, GreatEq = Value
	}
  
	// return a list of condition clauses given two tables
	def populate(connection: Connection, attrList : List[AttributeClause])  : Tuple2[List[UnaryCondition], List[BinaryCondition]] = {
		val binaryList: MutableList[BinaryCondition] = MutableList()
		val unaryList: MutableList[UnaryCondition] = MutableList()
		for (attr <- attrList) { 
		  for (op <- ConditionClause.Operator.values){
			for (attr2 <- attrList)
			 {
				 binaryList += new BinaryCondition(attr, op, attr2)  
			  }
			unaryList += new UnaryCondition(attr, op, attr.constList)
		  }
		}
		new Tuple2(unaryList.toList, binaryList.toList)
	}
}


