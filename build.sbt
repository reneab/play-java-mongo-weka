name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)

libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.2.2"
libraryDependencies += "com.google.code.gson" % "gson" % "2.3.1"
libraryDependencies += "nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.13"


fork in run := true