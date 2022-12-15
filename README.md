## 특이 사항
- Unit Test 작성 (RewardCalculationTests)
- React를 사용한 Frontend 구현
- select for update를 사용한 보상 지급 시 동시성 처리
- OpenAPI 스펙 작성(src/main/resources/document/api.yaml)

## 실행 방법
- Ubuntu 22.04 기준으로 작성한다.
- Docker container 생성
```bash
$ docker run --name musinsa-sample-ubuntu -d -it -p 8082:8082 -p 8081:8081 -p 4000:3306 ubuntu:22.04 bash
```

- Docker container 실행
```bash
$ docker exec -it musinsa-sample-ubuntu bash
```

- git repository clone
```bash
$ apt update
$ apt install git -y
$ mkdir code
$ cd /code
$ git clone https://github.com/hoyongjungdev/reward-sample-project.git
```

- 패키지 install
```bash
$ cd reward-sample-project
$ ./install.sh
```

- MySQL 스크립트 실행
```bash
$ mysql -u root -p
# 기본 비밀번호 = empty string

$ source /code/reward-sample-project/src/main/resources/db/migration.sql
$ exit
```

- 서버 실행
```bash
$ ./mvnw clean package -DskipTests
$ nohup java -jar -Dserver.port=8081 target/musinsasample-0.0.1-SNAPSHOT.jar &
```

- 클라이언트 실행
```bash
$ cd frontend
$ npm install
$ nohup npm run start &
```

- 프로세스 중지
```bash
$ ps -ef
# 프로세스 찾기

$ kill -9 [프로세스 번호]
```

## 시간 조작 후 테스트하는 방법
- time_intervals 테이블의 diff_in_hours 컬럼 값을 변경 시키면 시스템 시간을 조작할 수 있다.