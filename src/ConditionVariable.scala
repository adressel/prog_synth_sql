package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

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
	private var cvs : Vector[ConditionVariable] = Vector()
	def all = cvs
	
	object Operator extends Enumeration {
		type Operator = Value
		val Equal, NotEqual, Less, Greater, LessEq, GreatEq = Value
	}
  
	// return a Vector of condition clauses given two tables
	def populate(connection: Connection, attrVector : Vector[AttributeVariable]) = {
		val binaryVector: ArrayBuffer[BinaryCondition] = ArrayBuffer()
		val unaryVector: ArrayBuffer[UnaryCondition] = ArrayBuffer()
		for (attr <- attrVector) { 
		  for (op <- ConditionVariable.Operator.values){
			for (attr2 <- attrVector) {
				if (attr.attrType == attr2.attrType)
					binaryVector += new BinaryCondition(attr, op, attr2)  
			}
			for (const <- attr.constant) {
			  unaryVector += new UnaryCondition(attr, op, const)
			}
		  }
		}
		cvs = (binaryVector.toVector) ++ (unaryVector.toVector)
	}
}


