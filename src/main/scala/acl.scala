package acl

import org.neo4j.graphdb.{GraphDatabaseService, Direction, Node, Relationship, PropertyContainer, DynamicRelationshipType}
import org.neo4j.kernel.Traversal
import org.neo4j.cypher.ExecutionEngine

import javax.ws.rs.{Path, GET, POST, Produces, FormParam, Consumes}
import javax.ws.rs.core.Context
import javax.ws.rs.core.Response
import javax.ws.rs.core.MediaType

import scala.collection.JavaConverters._
import scala.annotation.tailrec

import net.liftweb.json._
import net.liftweb.json.Extraction._

@Path("/")
class acl(@Context db:GraphDatabaseService) {

  implicit val formats = Serialization.formats(NoTypeHints)

  @GET
  @Path("/init")
  @Produces(Array("application/json"))
  def init() = {
    val tx = db.beginTx
    try {
      val ee = new ExecutionEngine(db)
      ee.execute("CREATE CONSTRAINT ON (u:_User) ASSERT u._id IS UNIQUE")
      ee.execute("CREATE CONSTRAINT ON (g:_Group) ASSERT g._id IS UNIQUE")
      ee.execute("CREATE CONSTRAINT ON (p:_Permission) ASSERT p._id IS UNIQUE")
      ee.execute("CREATE CONSTRAINT ON (c:_Content) ASSERT c._id IS UNIQUE")
 
      tx.success
      Response.ok(MediaType.APPLICATION_JSON).build()
    } catch {
      case e:Exception => Response.status(500).entity(e.getMessage).build()
    } finally {
      tx.close
    }
  }

  case class IdProps(id:String,props:collection.mutable.Map[String,Any])

  @POST
  @Consumes(Array("text/plain", "application/json"))
  @Path("/users/create")
  @Produces(Array("application/json"))
  def userCreate(json:String) = {
    val tx = db.beginTx
    try {
      val params = parse(json).extract[IdProps]
      params.props += "_id" -> params.id
      val ee = new ExecutionEngine(db)
      ee.execute("MERGE (u:_User {_id:{props}._id}) ON CREATE SET u = {props}", params.props.toMap)
 
      tx.success
      Response.ok(MediaType.APPLICATION_JSON).build()
    } catch {
      case e:Exception => Response.status(500).entity(e.getMessage).build()
    } finally {
      tx.close
    }
  }

  @POST
  @Consumes(Array("text/plain", "application/json"))
  @Path("/users/update")
  @Produces(Array("application/json"))
  def userUpdate(json:String) = {
    val tx = db.beginTx
    try {
      val params = parse(json).extract[IdProps]
      params.props += "_id" -> params.id
      val ee = new ExecutionEngine(db)
      ee.execute("MATCH (u:_User) WHERE u._id = {props}._id SET u = {props}", params.props.toMap)
 
      tx.success
      Response.ok(MediaType.APPLICATION_JSON).build()
    } catch {
      case e:Exception => Response.status(500).entity(e.getMessage).build()
    } finally {
      tx.close
    }
  }

}
