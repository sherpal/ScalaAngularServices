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
