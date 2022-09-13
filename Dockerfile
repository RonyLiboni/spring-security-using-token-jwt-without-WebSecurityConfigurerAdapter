FROM eclipse-temurin:11.0.16.1_1-jre-jammy
WORKDIR /spring-security-jwt
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-security-with-token-jwt.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","spring-security-with-token-jwt.jar"]