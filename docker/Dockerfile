FROM openjdk:17

WORKDIR /app

# Instructions:
# To override the default environment variables, create a .env file
# in the same directory as this Dockerfile.
# In this file, specify the values for the following variables:
# For PostgresSQL
# - DB_URL: The database connection URL
# - DB_USER: The database username
# - DB_PASSWORD: The database password
# For Redis
# - DB_NAME: The database name
# For JWT token
# - SECRET: The application's secret key
# Additionally, ensure that the JAR file is present in the same directory as this Dockerfile.
COPY .env /app/.env

ARG APP_JAR=*.jar

COPY ${APP_JAR} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
