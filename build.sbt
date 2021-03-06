import sbt.Package.ManifestAttributes

organization in ThisBuild := "org.mitre"
version in ThisBuild := "1.0-SNAPSHOT"

resolvers += Resolver.mavenLocal
resolvers += Resolver.jcenterRepo
resolvers += "OmegaT Maven" at "https://dl.bintray.com/omegat-org/maven/"

lazy val root = (project in file("."))
  .enablePlugins(OsDetectorPlugin)
  .enablePlugins(AssemblyPlugin)
  .settings(
    name := "omegat-torchscript-plugin",
    crossPaths := false,
    autoScalaLibrary := false,
    Compile / packageBin / artifact := {
        val prev = (Compile / packageBin / artifact).value
        prev.withClassifier(Some(osDetectorClassifier.value))
    },
    assemblyJarName in assembly := {
        s"${name.value}-${version.value}-${osDetectorClassifier.value}.jar"
    },
    publishMavenStyle := true,
    packageOptions in assembly := Seq(ManifestAttributes(("OmegaT-Plugins", "org.mitre.pinball.omegat.pytorch.FairseqMachineTranslation"))),
    libraryDependencies ++= Seq(
      "org.mitre" % "jfairseq" % "1.0-SNAPSHOT" classifier osDetectorClassifier.value,
      "com.github.levyfan" %  "sentencepiece" %  "0.0.2" classifier osDetectorClassifier.value,
      "junit" % "junit" % "4.12" % Test,
      "org.pytorch" % "pytorch_java_only" % "1.7.1",
      "org.mitre" % "jfastbpe" % "1.0-SNAPSHOT",
      "oauth.signpost" % "signpost-core" % "1.2.1.2",
      "oauth.signpost" % "signpost-commonshttp4" % "1.2.1.2",
      "org.apache.httpcomponents" % "httpclient" % "4.5",
      "org.json" % "json" % "20160810",
      "com.intellij" % "forms_rt" % "7.0.3",
      "org.slf4j" % "slf4j-api" % "1.7.25",
      "org.omegat" % "omegat" % "4.3.0" % "provided",
      "commons-io" % "commons-io" % "2.5" % "provided",
      "commons-lang" % "commons-lang" % "2.6" % "provided",
      "org.apache.opennlp" % "opennlp-tools" % "1.9.2",
      "org.scalatest" %% "scalatest" % "3.2.2" % "test"
    )
  )
 
