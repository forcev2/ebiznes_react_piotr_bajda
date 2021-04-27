FROM piotrbajda/pbajda-ecommerce:latest
COPY ./target/scala-2.12/classes /tmp/target
EXPOSE 9000
WORKDIR /tmp/target/classes
ENTRYPOINT ["sbt","run"]