FROM maven:3.9-eclipse-temurin-17 AS builder
RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/ILabzin/bellProject.git /app
WORKDIR /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -Xmx512m -Xms512m -XX:+UseG1GC -XX:MaxGCPauseMillis=100 -XX:G1HeapRegionSize=2m -Xloggc:/app/gc.log -XX:+UseStringDeduplication -jar /app/app.jar"]
