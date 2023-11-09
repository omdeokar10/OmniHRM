#base
FROM openjdk:17
MAINTAINER kaustubh deokar <kaustubhd9@gmail.com>
EXPOSE 8081
ADD target/database-0.0.1-SNAPSHOT.jar database.jar
ENTRYPOINT ["java","-jar","/database.jar"]
# gets executed when container is built from the image.
CMD ["echo", "Container spun!"]
