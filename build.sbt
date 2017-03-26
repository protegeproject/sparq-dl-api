/**
  * This SBT build file has been added to allow SBT based projects to add a dependency to this library straight from
  * SBT to Github via the RootProject(uri(github-uri)) / dependsOn construct.
  *
  * Ensure that this file is kept in sync with the pom.xml file
  */
organization := "edu.stanford.protege"

name := "de-derivo-sparqldlapi"

version := "3.1.0"


scalaVersion := "2.12.1"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

libraryDependencies +=
  "net.sourceforge.owlapi" % "owlapi-distribution" % "5.0.5" withSources() withJavadoc()

libraryDependencies +=
  "org.jdom" % "jdom-legacy" % "1.1.3"

libraryDependencies +=
  "edu.stanford.protege" % "jpaul" % "2.5.1"

libraryDependencies +=
  "junit" % "junit" % "4.12" % "test"

libraryDependencies +=
  "org.mockito" % "mockito-core" % "2.7.19" % "test"
