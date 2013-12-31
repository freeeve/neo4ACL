#!/bin/bash
sbt package
cp target/scala*/neo4acl*.jar $1/lib/
wget http://repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.6/paranamer-2.6.jar -P $1/lib/
wget http://repo1.maven.org/maven2/net/liftweb/lift-json_2.10/2.5.1/lift-json_2.10-2.5.1.jar -P $1/lib/
