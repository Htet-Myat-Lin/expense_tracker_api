FROM maven:3.9.16-eclipse-temurin-25 AS builder
WORKDIR /workspace
COPY pom.xml ./
COPY .mvn .mvn
COPY src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:25-jdk
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport"
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_TOOL_OPTIONS $JAVA_OPTS -jar /app/app.jar --server.port=${PORT:-8080}"]
