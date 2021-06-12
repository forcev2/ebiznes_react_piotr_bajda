name := "Ebiznes_v2"
 
version := "1.0" 
      
lazy val `ebiznes_v2` = (project in file(".")).enablePlugins(PlayScala)

//resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

resolvers += "Atlassian's Maven Public Repository" at "https://packages.atlassian.com/maven-public/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  ehcache , ws , specs2 % Test , guice,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "org.xerial"        %  "sqlite-jdbc"           % "3.31.1",
  "com.iheart" %% "ficus" % "1.4.7",
  "com.mohiva" %% "play-silhouette" % "6.1.1",
  "com.mohiva" %% "play-silhouette-password-bcrypt" % "6.1.1",
  "com.mohiva" %% "play-silhouette-persistence" % "6.1.1",
  "com.mohiva" %% "play-silhouette-crypto-jca" % "6.1.1",
  "com.mohiva" %% "play-silhouette-totp" % "6.1.1",
  "net.codingwell" %% "scala-guice" % "4.2.6"
)



unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      