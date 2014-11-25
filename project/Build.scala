import sbt._
import Keys._

object Play2MorphiaPluginBuild extends Build {

  import Resolvers._
  import Dependencies._
  import BuildSettings._

  lazy val Play2MorphiaPlugin = Project(
    "play2-morphia-plugin",
    file("."),
    settings = buildSettings ++ Seq(
      libraryDependencies := runtime ++ test,
      publishMavenStyle := true,
      publishTo := Some(luqasnLocalRepository),
      scalacOptions ++= Seq("-Xlint", "-deprecation", "-unchecked", "-encoding", "utf8"),
      javacOptions ++= Seq("-source", "1.6", "-target", "1.6", "-encoding", "utf8"),
      javacOptions in doc := Seq("-source", "1.6"),
      resolvers ++= Seq(DefaultMavenRepository, Resolvers.typesafeRepository),//, Resolvers.morphiaRepository),
      checksums := Nil // To prevent proxyToys downloding fails https://github.com/leodagdag/play2-morphia-plugin/issues/11
    )
  ).settings()

  object Resolvers {
    val githubRepository = Resolver.file("GitHub Repository", Path.userHome / "dev" / "leodagdag.github.com" / "repository" asFile)(Resolver.ivyStylePatterns)
    val dropboxReleaseRepository = Resolver.file("Dropbox Repository", Path.userHome / "Dropbox" / "Public" / "repository" / "releases" asFile)
    val dropboxSnapshotRepository = Resolver.file("Dropbox Repository", Path.userHome / "Dropbox" / "Public" / "repository" / "snapshots" asFile)
    val typesafeRepository = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
    val luqasnRepository = "luqasn GitHub" at "http://luqasn.github.com/repo/"
    val luqasnLocalRepository = Resolver.file("Github Pages", Path.userHome / "Projekte" / "luqasn.github.com" / "repo" asFile)
  }

  object Dependencies {
    val runtime = Seq(
      "org.mongodb.morphia" % "morphia" % "0.108",
      "org.mongodb.morphia" % "morphia-logging-slf4j" % "0.108",
      "org.mongodb.morphia" % "morphia-validation" % "0.108",
      //"org.mongodb" % "mongo-java-driver" % "2.11.0",
      "com.typesafe.play" %% "play-java" % buildPlayVersion % "provided"
    )
    val test = Seq(
      "com.typesafe.play" %% "play-test" % buildPlayVersion % "test"
    )
    /*
    val runtime = Seq(
        "com.github.jmkgreen.morphia" % "morphia" % "1.2.2",
        ("com.github.jmkgreen.morphia"    % "morphia-logging-slf4j" % "1.2.2" % "compile" notTransitive())
          .exclude("org.slf4j","slf4j-simple")
          .exclude("org.slf4j","slf4j-jdk14"),
        ("com.github.jmkgreen.morphia"    % "morphia-validation"    % "1.2.2" % "compile" notTransitive())
          .exclude("org.slf4j","slf4j-simple")
          .exclude("org.slf4j","slf4j-jdk14"),
        "com.typesafe.play"                       %% "play"                 % buildPlayVersion % "provided",
        "com.typesafe.play"                       %% "play-java"             % buildPlayVersion % "provided",
        ("org.springframework"       % "spring-core"           % "3.0.7.RELEASE" % "compile" notTransitive())
          .exclude("org.springframework", "spring-asm")
          .exclude("commons-logging", "commons-logging"),
        ("org.springframework"       % "spring-beans"          % "3.0.7.RELEASE" % "compile" notTransitive())
          .exclude("org.springframework", "spring-core")
      )
      val test = Seq(
        "com.typesafe.play" %% "play-test" % buildPlayVersion % "test"
      )
    */
  }

  object BuildSettings {
    val buildOrganization = "leodagdag"
    val buildVersion      = "0.0.16"
    val buildScalaVersion = "2.10.0"
    val buildPlayVersion  = "2.2.1"
    val buildSettings = Defaults.defaultSettings ++ Seq (
      organization   := buildOrganization,
      version        := buildVersion,
      scalaVersion   := buildScalaVersion,
      scalaBinaryVersion  := CrossVersion.binaryScalaVersion(buildScalaVersion)
    )
  }
}
