#base
FROM openjdk:17
MAINTAINER kaustubh deokar <kaustubhd9@gmail.com>
EXPOSE 8086
ADD target/geolocation.jar geolocation.jar
ENTRYPOINT ["java","-jar","/geolocation.jar"]
# gets executed when container is built from the image.
CMD ["echo", "Container spun!"]