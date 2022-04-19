FROM openjdk:17
COPY ./target/classes/reseau/client /tmp
WORKDIR /tmp
ENTRYPOINT ["java","client"]