FROM eclipse-temurin:11
LABEL maintainer="renatoctorres@gmail.com"
WORKDIR /app
COPY build/libs/hubla-poc-1.0-SNAPSHOT.jar app/app.jar
CMD ["java", "com.hubla.challenge.ChallengeApplication.main"]
EXPOSE 8080
ENTRYPOINT ["java", "-jar","app/app.jar"]
