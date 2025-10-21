FROM maven:3.9.9-eclipse-temurin-17
WORKDIR /app
COPY pom.xml ./
RUN mvn -q -DskipTests -Pnone dependency:resolve dependency:resolve-plugins || true
COPY . .
CMD ["mvn", "-q", "-e", "-DskipTests=false", "test"]