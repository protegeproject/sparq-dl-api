/**
  * This SBT build file has been added to allow SBT based projects to add a dependency to this library straight from
  * SBT to Github via the RootProject(uri(github-uri)) / dependsOn construct.
  *
  * Ensure that this file is kept in sync with the pom.xml file
  */
organization := "edu.stanford.protege"

name := "de-derivo-sparqldlapi"

version := "3.0.0"

scalaVersion := "2.12.1"

val owlApiVersion = "5.0.5" // 4.1.3

libraryDependencies += ("net.sourceforge.owlapi" % "owlapi-api" % owlApiVersion withSources())
  .exclude("com.fasterxml.jackson.core", "jackson-core")

libraryDependencies += ("net.sourceforge.owlapi" % "owlapi-distribution" % owlApiVersion withSources())

libraryDependencies += "org.jdom" % "jdom-legacy" % "1.1.3"

libraryDependencies += "edu.stanford.protege" % "jpaul" % "2.5.1"

libraryDependencies += "junit" % "junit" % "3.8.1" % "test"

libraryDependencies += "org.mockito" % "mockito-core" % "2.0.5-beta" % "test"
