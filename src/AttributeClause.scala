package src
import java.sql.Connection

class AttributeClause (tableName: String, attrName: String) extends Clause {
	
}

object AttributeClause {
	def test = {
		println("hi")
	}
	//creates the list of attributes given two tables
	def populate(connection: Connection, tableName1 : String, tableName2 : String)  = {
		
	}
}