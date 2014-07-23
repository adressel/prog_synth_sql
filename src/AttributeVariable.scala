package src
import java.sql.Connection
import scala.collection.mutable.ArrayBuffer

// AttributeVariable is responsible for keeping track of the possible values 
// that can exist in a certain attribute

class AttributeVariable(
  val tableName: String,
  val attrName: String,
  val constant: Vector[Any],
  val max: Any,
  val min: Any,
  val attrType: String) extends Variable {

  override def toString() = s"$tableName $attrName $attrType ${constant.mkString(",")} max: $max and min: $min\n"

  override def name = s"$tableName.$attrName"

  val constVector = constant.toVector
}

object AttributeVariable {
  private var attrs: Vector[AttributeVariable] = Vector()

  def all = attrs

  def populate = {
    val x: ArrayBuffer[AttributeVariable] = ArrayBuffer()
    var i = 0
    val output_var = OutputVariable.good
    var columnNum = 0

    for (table <- Data.table_names) {

      val attributes = Utility.getTableAttrs(table)

      for (i <- 0 until attributes.length) {
        // get a list of all unique constants that occur for this attribute, from this table        
        val constantSet = Data.connection.createStatement().executeQuery(s"select distinct ${attributes(i)._1} from $table;");

        // convert the constantSet to an ArrayBuffer
        val constVector: ArrayBuffer[Any] = ArrayBuffer()
        while (constantSet.next()) {
          constVector += constantSet.getObject(attributes(i)._1)
        }

        // varchar, date, char, and timestamp are attribute types that need to have their values wrapped
        // in quotes.  Like name = "Bob" versus name = Bob
        if (attributes(i)._2 == "varchar" || attributes(i)._2 == "date"
          || attributes(i)._2 == "char" || attributes(i)._2 == "timestamp") {
          var tmpattr: Set[String] = output_var.map(x => x._1(i + columnNum).toString).toSet
          x += new AttributeVariable(table, attributes(i)._1, constVector.toVector, tmpattr.max, tmpattr.min, attributes(i)._2)
        } // otherwise, no quotes are necessary
        else {
          var tmpattr: Set[Double] = output_var.map(x => (x._1(i + columnNum).toString).toDouble).toSet
          x += new AttributeVariable(table, attributes(i)._1, constVector.toVector, tmpattr.max, tmpattr.min, attributes(i)._2)
        }
      }
      columnNum += attributes.length
    }
    attrs = x.toVector
  }
}