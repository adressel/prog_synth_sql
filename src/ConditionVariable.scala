package src
import java.sql.Connection
import scala.collection.mutable.MutableList

class ConditionVariable(
    left : AttributeVariable, 
    op : ConditionVariable.Operator.Value
) extends Variable 
{}

class UnaryCondition(
    left : AttributeVariable, 
    op : ConditionVariable.Operator.Value,
    right: Any
)
extends ConditionVariable(left, op) {
	override def toString() = left + "  " + op + "  " + right + "\n"
}

class BinaryCondition(
    left : AttributeVariable, 
    op : ConditionVariable.Operator.Value,
    right: AttributeVariable
) extends ConditionVariable(left, op) {
	override def toString() = left + "  " + op + "  " + right + "\n"
}

object ConditionVariable {
	object Operator extends Enumeration {
		type Operator = Value
		val Equal, NotEqual, Less, Greater, LessEq, GreatEq = Value
	}
  
	// return a list of condition clauses given two tables
	def populate(connection: Connection, attrList : List[AttributeClause])  : List[ConditionVariable] = {
		val binaryList: MutableList[BinaryCondition] = MutableList()
		val unaryList: MutableList[UnaryCondition] = MutableList()
		for (attr <- attrList) { 
		  for (op <- ConditionVariable.Operator.values){
			for (attr2 <- attrList)
			 {
				if (attr.typeAttr == attr2.typeAttr)
				 binaryList += new BinaryCondition(attr, op, attr2)  
			  }
			for (const <- attr.constant)
			{
			  unaryList += new UnaryCondition(attr, op, const)
			}
			
		  }
		}
		(binaryList.toList) ::: (unaryList.toList)
	}
}


