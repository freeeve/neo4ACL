name := "neo4acl"

version := "0.1"
 
scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
  "org.neo4j" % "neo4j" % "2.0.0",
  "org.apache.cxf" % "cxf-bundle-jaxrs" % "2.7.5",
  "net.liftweb" %% "lift-json" % "2.5"
)
