FROM openjdk:17
LABEL author=kaustubhd9@gmail.com
EXPOSE 8081

ADD ./target/hrm-backend-1.jar backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]
CMD ["echo","backend up"]