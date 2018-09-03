/// variables

val groupId = "$group_id$"
val projectName = "$project_name$"
val gitHubOwner = "$github_owner$"

/// projects

lazy val root = project
  .in(file("."))
  .aggregate(readme)
  .settings(commonSettings)
  .settings(noPublishSettings)

lazy val readme = project
  .in(file("modules/readme"))
  .enablePlugins(TutPlugin)
  .settings(commonSettings)
  .settings(noPublishSettings)
  .settings(
    fork in Tut := true,
    scalacOptions -= "-Ywarn-unused:imports",
    tutSourceDirectory := baseDirectory.value,
    tutTargetDirectory := (LocalRootProject / baseDirectory).value
  )

/// settings

lazy val commonSettings = Def.settings(
  compileSettings,
  metadataSettings,
  scaladocSettings
)

lazy val compileSettings = Def.settings()

lazy val metadataSettings = Def.settings(
  name := projectName,
  organization := groupId,
  homepage := Some(url(s"https://github.com/\$gitHubOwner/\$projectName")),
  startYear := Some(2018),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  headerLicense := Some(HeaderLicense.ALv2("2018", s"\$projectName contributors")),
  developers := List(
    Developer(
      id = "fthomas",
      name = "Frank S. Thomas",
      email = "",
      url(s"https://github.com/fthomas")
    )
  )
)

lazy val noPublishSettings = Def.settings(
  skip in publish := true
)

lazy val scaladocSettings = Def.settings()

/// commands

def addCommandsAlias(name: String, cmds: Seq[String]) =
  addCommandAlias(name, cmds.mkString(";", ";", ""))

addCommandsAlias(
  "validate",
  Seq(
    "clean",
    "headerCheck",
    "scalafmtCheck",
    "scalafmtSbtCheck",
    "test:scalafmtCheck",
    "coverage",
    "test",
    "coverageReport",
    "doc",
    "readme/tut",
    "package",
    "packageSrc"
  )
)
