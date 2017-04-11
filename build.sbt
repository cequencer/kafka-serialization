import com.typesafe.sbt.{GitBranchPrompt, GitVersioning}

def OvoRootProject(name: String) = Project(name, file("."))

def OvoProject(name: String) = Project(name, file(name))

lazy val noPublish = Seq(
  publish := {},
  publishArtifact := false
)

lazy val `kafka-serialization` = OvoRootProject("kafka-serialization")
  .settings(Shared.settings: _*)
  .settings(noPublish: _*)
  .settings(Tut.settings: _*)
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .aggregate(avro)
  .aggregate(avro4s)
  .aggregate(circe)
  .aggregate(spray)
  .aggregate(client)
  .aggregate(core)
  .aggregate(json4s)
  .aggregate(testkit)

lazy val testkit = OvoProject("testkit")
  .settings(
    name := "kafka-serialization-testkit"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.testkit)
  .settings(noPublish: _*)
  .settings(Git.settings: _*)

lazy val json4s = OvoProject("json4s")
  .settings(
    name := "kafka-serialization-json4s"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.json4s)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .settings(libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value)
  .dependsOn(core)
  .dependsOn(testkit % "test->test")

lazy val avro = OvoProject("avro")
  .settings(
    name := "kafka-serialization-avro"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.avro)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .dependsOn(core)
  .dependsOn(testkit % "test->test")

lazy val avro4s = OvoProject("avro4s")
  .settings(
    name := "kafka-serialization-avro4s"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.avro4s)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .dependsOn(core)
  .dependsOn(avro)
  .dependsOn(testkit % "test->test")

lazy val circe = OvoProject("circe")
  .settings(
    name := "kafka-serialization-circe"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.circe)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .dependsOn(core)
  .dependsOn(testkit % "test->test")

lazy val spray = OvoProject("spray")
  .settings(
    name := "kafka-serialization-spray"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.spray)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .dependsOn(core)
  .dependsOn(testkit % "test->test")

lazy val core = OvoProject("core")
  .settings(
    name := "kafka-serialization-core"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.core)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .dependsOn(testkit % "test->test")

lazy val client = OvoProject("client")
  .settings(
    name := "kafka-serialization-client"
  )
  .settings(Shared.settings: _*)
  .settings(Dependencies.client)
  .settings(Bintray.settings: _*)
  .settings(Git.settings: _*)
  .settings(Defaults.itSettings: _*)
  .dependsOn(core)
  .dependsOn(testkit % "test->test")
  .dependsOn(testkit % "it->test")
  .dependsOn(testkit % "it->compile")
  .configs(IntegrationTest)