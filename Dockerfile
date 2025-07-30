FROM khipu/openjdk21-alpine

COPY target/paul-be.jar /apapun/paul-be.jar
CMD ["java","-jar","/apapun/paul-be.jar"]