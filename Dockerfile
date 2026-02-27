FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY stayfinder .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java","-jar","target/stayfinder-0.0.1-SNAPSHOT.jar"]