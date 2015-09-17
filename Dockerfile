FROM java:8-jre
MAINTAINER antono@clemble.com

EXPOSE 10014

ADD ./buildoutput/search-*-SNAPSHOT.jar /data/search.jar

CMD java -jar -Dspring.profiles.active=cloud -Dlogging.config=classpath:logback.cloud.xml -Dserver.port=10014 /data/search.jar
