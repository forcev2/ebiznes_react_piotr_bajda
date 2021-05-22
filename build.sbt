name := "Ebiznes_v2"
 
version := "1.0" 
      
lazy val `ebiznes_v2` = (project in file(".")).enablePlugins(PlayScala)

//resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  ehcache , ws , specs2 % Test , guice,
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3",
  "com.typesafe.play" %% "play-slick" % "4.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0",
  "org.xerial"        %  "sqlite-jdbc"           % "3.31.1"
)



unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      