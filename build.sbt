name := "Ebiznes_v2"
 
version := "1.0" 
      
lazy val `ebiznes_v2` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq( ehcache , ws , specs2 % Test , guice )
libraryDependencies += "org.scalatestplus" %% "scalacheck-1-15" % "3.2.8.0" % "test"
libraryDependencies +=  "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies +=  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"
libraryDependencies +=  "org.xerial"        %  "sqlite-jdbc" % "3.30.1"




unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      