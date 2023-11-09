#base
FROM openjdk:17
MAINTAINER kaustubh deokar <kaustubhd9@gmail.com>
EXPOSE 8080
ADD target/Logtime-0.0.1-SNAPSHOT.jar logtimefinal.jar
ENTRYPOINT ["java","-jar","/logtimefinal.jar"]
# gets executed when container is built from the image.
CMD ["echo", "Container spun!"]