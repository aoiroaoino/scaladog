import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

val Scala2_13 = "2.13.0"
val Scala2_12 = "2.12.8"

val supportedScalaVersions = List(Scala2_13, Scala2_12)

ThisBuild / scalaVersion := Scala2_13
ThisBuild / organization := "dev.nomadblacky"
ThisBuild / organizationName := "NomadBlacky"

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Xfatal-warnings",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen"
    )
)

lazy val scaladog = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "scaladog",
    crossScalaVersions := supportedScalaVersions,
    libraryDependencies ++= Seq(
        "com.lihaoyi"   %% "requests"   % "0.2.0",
        "com.lihaoyi"   %% "upickle"    % "0.8.0",
        "com.beachape"  %% "enumeratum" % "1.5.13",
        "org.scalatest" %% "scalatest"  % "3.0.8" % Test
      ),
    releaseCrossBuild := true,
    releaseProcess := Seq[ReleaseStep](
        checkSnapshotDependencies,
        inquireVersions,
        runClean,
        runTest,
        setReleaseVersion,
        ReleaseProcesses.setReleaseVersionToReadme,
        commitReleaseVersion,
        tagRelease,
        releaseStepCommandAndRemaining("+publishSigned"),
        setNextVersion,
        commitNextVersion,
        releaseStepCommand("sonatypeReleaseAll"),
        pushChanges
      )
  )

lazy val integrationTests = (project in file("integrationTests"))
  .dependsOn(scaladog % "test->test;compile->compile")
  .settings(commonSettings)
  .settings(
    crossScalaVersions := supportedScalaVersions,
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  )
