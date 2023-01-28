FROM openjdk:18
RUN groupadd spring && useradd -g spring spring
USER root
RUN mkdir -p /files
RUN chown -R spring:spring /files
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
EXPOSE 8080
# COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "cz.filmdb.FilmDBServerApplication"]
