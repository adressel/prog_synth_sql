package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

// A condition variable represents a select query

abstract class ConditionVariable(
  left: AttributeVariable,  // the left operand of this condition 
  op: String                // an operator string e.g. <=, =, >=
) extends Variable {
  def clause: String

  // query represents the string associated with the cv
  def query: String
}

// a UnaryCondition is a class that inherits from ConditionVariable.  It
// represents a single attribute compared (=, <=, >=) with a literal.
class UnaryCondition(
  left: AttributeVariable,   // same as ConditionVariable
  op: String,                // same as ConditionVariable
  right: Any                 // a constant that is the right operand
)
  extends ConditionVariable(left, op) {
  // wraps string literals in quotes so that SQL can understand.  
  // ex: user.name = "Bob" vs. user.name = Bob
  def rightQuery = {
    right match {
      case x: String => "\"" + x + "\""
      case x: Vector[Any] =>
        println(""); System.exit(1)
      case _ => right.toString
    }
  }

  override def print = query

  // toString() overridden for debugging purposes

  override def toString() = s"$left $op $right\n"
  override def clause = s"${left.name} $op $rightQuery"
  def query = s"select * from ${Data.desired_tables} where ${clause}"
}

// A binary condition specifically deals with comparisons between two attributes
class BinaryCondition(
  left: AttributeVariable,  // left operand
  op: String,               // operand string
  right: attributeVariable  // right operand
) extends ConditionVariable(left, op) {

  override def print = query

  override def toString() = s"$left $op $right"

  override def clause = s"${left.name} $op ${right.name}"

  def query = s"select * from ${Data.desired_tables} where ${clause}"
}

object ConditionVariable {
  // A vector of binary conditions
  private var cv_binary: Vector[ConditionVariable] = Vector()

  // A vector of unary conditions
  private var cv_unary: Vector[ConditionVariable] = Vector()
 
  def all = cv_unary ++ cv_binary // A method to get "all" condition variables
  def get_binary = cv_binary
  def get_unary = cv_unary

  // creates and adds binary condition variables to cv_binary given an "op" string.
  // If op was "=", then binary cvs like "user.username = album.owner" would be created.
  // If op was "<", then binary cvs like "user.username < album.owner" would be created.
  def populate_binary(op: String) = {
    val attrVector = AttributeVariable.all

    // a temporary ArrayBuffer of binary cvs to build up
    val binaryVector: ArrayBuffer[BinaryCondition] = ArrayBuffer()

    var i = 0
    var j = 0
    // iterate through every attribute
    for (i <- 0 until (attrVector.length - 1)) {
      // a second loop to guarantee every combination of attributes will be considered
      for (j <- (i + 1) until attrVector.length) {
        // only create a binary cv is the two attributes are the same type
        if (attrVector(i).attrType == attrVector(j).attrType)
          binaryVector += new BinaryCondition(attrVector(i), op, attrVector(j))
      }
    }
    // add the generated cvs to cv_binary
    cv_binary = cv_binary ++ (binaryVector.toVector)
  }

  // populate unary cvs. 
  // ARGUMENTS:
  //	op - a string such as "=" or "<=" that indicates what sort of variables will be created
  //	accept-type - a string that indicates what type of attributes will be genereated 
  //		e.g. "String" or "Integer"
  def populate_unary(op: String, accept_type: Vector[String]) = {
    val attrVector = AttributeVariable.all
    // temporary ArrayBuffer to build up Unary Conditions
    val unaryVector: ArrayBuffer[UnaryCondition] = ArrayBuffer()

    // iterate each attributeVariable
    for (attr <- attrVector) {
      // only create unary conditions if it is the accepted attribute type
      if (!accept_type.contains(attr.attrType)) {
        // create a unary cv for EVERY constant associated with this attribute
        for (const <- attr.constant)
          unaryVector += new UnaryCondition(attr, op, const)
      }
    }
    cv_unary = cv_unary ++ (unaryVector.toVector)
  }

  // creates unary variables with ONLY the max/min of each table
  // max will be compared with <=, while min will be compared with >=
  def populate_unary_max_min = {
    val attrVector = AttributeVariable.all

    // temp ArrayBuffer for populating
    val unaryVector: ArrayBuffer[UnaryCondition] = ArrayBuffer()
    // iterate every attribute variable
    for (attr <- attrVector) {
    	// if this attributes max = min, then there is only ONE possible value for this variable
      if (attr.max == attr.min)
        unaryVector += new UnaryCondition(attr, "=", attr.max)
      else {
        unaryVector += new UnaryCondition(attr, "<=", attr.max)
        unaryVector += new UnaryCondition(attr, ">=", attr.min)
      }
    }
    cv_unary = cv_unary ++ (unaryVector.toVector)
  }
}
