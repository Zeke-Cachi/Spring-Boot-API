FROM amazoncorretto:17
WORKDIR /app
COPY target/job_applicants-0.0.1-SNAPSHOT.jar /app/job_applicants-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "job_applicants-0.0.1-SNAPSHOT.jar"]
