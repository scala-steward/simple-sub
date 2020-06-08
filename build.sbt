import Wart._

enablePlugins(ScalaJSPlugin)

ThisBuild / scalaVersion     := "2.13.1"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.lptk"
ThisBuild / organizationName := "LPTK"

lazy val root = project.in(file("."))
  .aggregate(simplesubJS, simplesubJVM)
  .settings(
    publish := {},
    publishLocal := {}
  )

lazy val simplesub = crossProject(JSPlatform, JVMPlatform).in(file("."))
  .settings(
    name := "simple-algebraic-subtyping",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-language:higherKinds",
      "-Ywarn-value-discard",
    ),
    wartremoverWarnings ++= Warts.allBut(
      Recursion, Throw, Nothing, Return, While,
      Var, MutableDataStructures, NonUnitStatements,
      DefaultArguments, ImplicitParameter, StringPlusAny,
      JavaSerializable, Serializable, Product,
      LeakingSealed,
      Option2Iterable, TraversableOps,
    ),
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    libraryDependencies += "com.lihaoyi" %%% "sourcecode" % "0.2.1",
    libraryDependencies += "com.lihaoyi" %%% "fastparse" % "2.2.4",
  )
  .jsSettings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.8",
    libraryDependencies += "be.doeraene" %%% "scalajs-jquery" % "0.9.6",
  )

lazy val simplesubJVM = simplesub.jvm.settings(
  libraryDependencies += "com.lihaoyi" %% "os-lib" % "0.6.2",
)
lazy val simplesubJS = simplesub.js
