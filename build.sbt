ThisBuild / scalaVersion := "2.13.8"

lazy val root = project.in(file("."))
  .settings(
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % Test,
    libraryDependencies += "org.eclipse.lsp4j" % "org.eclipse.lsp4j" % "0.9.0",
    scalacOptions ++= Seq(
      "-deprecation"
    )
  )
