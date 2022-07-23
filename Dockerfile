# Docker 환경 구성
FROM java:8

# 해당 이미지를 관리하는 사람
LABEL maintainer="yeri"

# 컨테이너가 필요한 데이터를 저장하는 곳
VOLUME /tmp

# 외부에 노출되는 포트 번호
EXPOSE 8090

# 현재 JAR 파일 변수 설정
ARG JAR_FILE=build/libs/re_chat_server-0.0.1-SNAPSHOT.jar

# demo.jar의 이름으로 JAR 파일 추가
ADD ${JAR_FILE} chatting-server.jar

# 컨테이너 실행 시 실행될 명령어 "java -jar chatting-server.jar"
ENTRYPOINT ["java", "-jar", "chatting-server.jar"]