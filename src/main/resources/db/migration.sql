# 사용자 생성

create user 'musinsasample'@'%' identified by 'my-secret-code';

# 데이터베이스 생성

create database musinsasample;
grant all privileges on musinsasample.* to 'musinsasample'@'%';

# 시간 조작을 위한 time_intervals 테이블 생성

use musinsasample;

create table time_intervals (
    id int auto_increment primary key,
    diff_in_hours int not null
);

insert into time_intervals (diff_in_hours) values (0);

# reward_counts 테이블 생성

create table reward_counts (
    reward_date date not null primary key,
    reward_claimed int not null
);

# reward_histories 테이블 생성

create table reward_histories (
    id integer auto_increment primary key,
    issued_at datetime not null,
    username varchar(50) not null,
    amount integer not null,
    consecutive_day integer not null
);

# users 테이블 생성

create table users (
    id integer auto_increment primary key,
    username varchar(50) not null,
    point integer not null
);
