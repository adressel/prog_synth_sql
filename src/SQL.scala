package src
import scala.util.Random
import java.io.PrintWriter
import scala.sys.process._

object SQL {
  
  val root = s"${Data.root}sql"
  
  val num_users = 200
  val num_albums = 100
  val num_photos = 100
  val num_contains = 100

  val rseed = 100;
  val gen = new Random(rseed)
  
  def load_data = {
    val users = (0 until num_users).map(_ => new User)
    val albums = (0 until num_albums).map(_ => new Album(i(num_users).toString))
    val photos = (0 until num_photos).map(_ => new Photo)
    val contains = (0 until num_contains).map(_ => new Contain(i(num_albums).toString, i(num_photos)))

    var SQL = new StringBuilder
    SQL ++= "drop database test;\n create database test;\n use test;\n"
    SQL ++= User.create_SQL
    SQL ++= Album.create_SQL
    SQL ++= Photo.create_SQL
    SQL ++= Contain.create_SQL
    SQL ++= "insert into user values"
    SQL ++= users.map(x => x.insert_SQL).mkString(",") + ";\n"
    SQL ++= "insert into album values"
    SQL ++= albums.map(x => x.insert_SQL).mkString(",") + ";\n"
    SQL ++= "insert into photo values"
    SQL ++= photos.map(x => x.insert_SQL).mkString(",") + ";\n"
    SQL ++= "insert into contain values"
    SQL ++= contains.map(x => x.insert_SQL).mkString(",") + ";\n"
    
    val out = new PrintWriter(s"$root/load.sql")
    out.println(SQL.toString)
    out.close
    
    Utility.execute("drop database test")
    Utility.execute("create database test")
    Utility.execute("use test")
    Utility.execute(User.create_SQL)
    Utility.execute(Album.create_SQL)
    Utility.execute(Photo.create_SQL)
    Utility.execute(Contain.create_SQL)
    Utility.execute("insert into user values" + users.map(x => x.insert_SQL).mkString(","))
    Utility.execute("insert into album values" + albums.map(x => x.insert_SQL).mkString(","))
    Utility.execute("insert into photo values" + photos.map(x => x.insert_SQL).mkString(","))
    Utility.execute("insert into contain values" + contains.map(x => x.insert_SQL).mkString(","))
  }
  
  def i(max: Int) = {
    gen.nextInt(max)
  }
  
  def s(len: Int) = {
    gen.alphanumeric.take(len).mkString
  }
}

class User {
  val username : String = User.get_id
  val age : Int = SQL.i(5)
  val firstname: String = SQL.s(10)
  val lastname: String = SQL.s(10)
  val height: Int = SQL.i(100)
  val hometown: String = SQL.s(10)
  
  def insert_SQL = s"\n('$username', $age, '$firstname', '$lastname', $height, '$hometown')"
}

object User {
  var id = 0
  def get_id = {
    id += 1
    id.toString
  }
  
  def create_SQL = {
    "create table user (username varchar(20), age integer, firstname varchar(20)," +
    "lastname varchar(20), height integer, hometown varchar(20));\n"
  }
}

class Album (
  owner: String
){
  val albumname : String = Album.get_id
  val created : Int = SQL.i(100)
  val modified : Int = created + SQL.i(50)
  
  def insert_SQL = s"\n('$owner', '$albumname', $created, $modified)"
}

object Album {
  var id = 0
  def get_id = {
    id += 1
    id.toString
  }
  
  def create_SQL = {
    "create table album (owner varchar(20), albumname varchar(20), created integer, modified integer);\n"
  }
}

class Contain (
  albumname : String,
  picid : Int
) {
  def insert_SQL = s"\n('$albumname', $picid)"
}

object Contain {
  def create_SQL = {
    "create table contain(albuname varchar(20), picid integer);\n"
  }
}

class Photo(
) {
  val picid : Int = Photo.get_id
  val description : String = SQL.s(10)
  val caption : String = SQL.s(10)
  val views : Int = SQL.i(1000)
  val created : Int = SQL.i(100)
  val modified : Int = created + SQL.i(50)
  
  def insert_SQL = s"\n($picid, '$description', '$caption', $views, $created, $modified)"
}

object Photo {
  var id = 0
  def get_id = {
    id += 1
    id
  }
  
  def create_SQL = {
    "create table photo(picid integer, description varchar(20), caption varchar(20), " +
    "views integer, created integer, modified integer);\n"
  }
}