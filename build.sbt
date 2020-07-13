name := "AngularServices"

version := "0.1"

scalaVersion := "2.13.2"

/** npm module will have version "0.1.0" */
scalaTsModuleVersion := (_ + ".0")

/** Enabling ScalaJS */
enablePlugins(ScalaJSPlugin)

/** Enabling ScalaTS */
enablePlugins(ScalaTsPlugin)

/** Enabling Scalably typed, with scala-js-bundler */
enablePlugins(ScalablyTypedConverterPlugin)

val copyFileTask = taskKey[Unit]("Copy the compiled files into Angular modules")

copyFileTask := {
  IO.copyDirectory(
    baseDirectory.value / "target" / "scala-2.13" / "scalajs-bundler" / "main",
    baseDirectory.value / "webapp" / "FromScalaWithLove" / "node_modules" / "scala-module"
  )
}

addCommandAlias("makeModule", ";scalaTsFastOpt;copyFileTask")
