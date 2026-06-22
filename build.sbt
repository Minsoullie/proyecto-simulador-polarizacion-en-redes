name := "ProyectoPolarizacion"
version := "1.0"
scalaVersion := "2.13.12"

// org.scalameter-core: medición de tiempos (config/withWarmer/measure),
// usado por el paquete `Benchmark` (provisto por el profesor).
// IMPORTANTE: Benchmark vive en src/main/scala (no en src/test/scala),
// así que esta dependencia NO puede llevar el scope "% Test", o el
// proyecto principal no compilará.
libraryDependencies += "com.storm-enroute" %% "scalameter-core" % "0.21"

// plotly-scala (render estático a HTML), usado por Benchmark.simEvolucion
// para generar los archivos .html con la evolución de la polarización.
libraryDependencies += "org.plotly-scala" %% "plotly-render" % "0.8.5"

// Habilita scala.collection.parallel.CollectionConverters, usado en
// Opinion/package.scala (rhoPar, confBiasUpdatePar) para paralelismo de datos.
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-language:implicitConversions"
)
