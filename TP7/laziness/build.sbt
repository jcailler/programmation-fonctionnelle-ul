name := "laziness"
scalaVersion := "3.3.1"
libraryDependencies += "org.scalameta" %% "munit" % "1.0.0-M10" % Test
scalacOptions ++= Seq("-source:future", "-deprecation", "-language:fewerBraces", "-Xfatal-warnings")
