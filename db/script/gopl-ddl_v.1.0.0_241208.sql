-- # =========================================================================================
-- #  || 계정 생성 및 권한 부여
-- # =========================================================================================

-- 데이터베이스 생성
CREATE DATABASE GOPL_DB;

-- 생성된 데이터베이스 사용
USE GOPL_DB;


-- 개발을 위한 데이터베이스 작업 및 관리를 수행할 게정 생성
CREATE USER 'suihin'@'%' IDENTIFIED BY 'tq5033';

-- 생성된 계정에게 현재 사용 중인 데이터베이스(방금 생성한 데이터베이스)에 대한 권한 부여
GRANT ALL PRIVILEGES ON GOPL_DB.* TO 'suihin'@'%';

-- 새로고침
FLUSH PRIVILEGES;


-- # ==========================================================================================================
-- # 	테이블 정의
-- # ==========================================================================================================

-- TABLE NAME : 사용자 테이블 / USERS
CREATE TABLE USERS (
	USER_ID						BIGINT						NOT NULL AUTO_INCREMENT COMMENT '사용자 번호',
	ID							VARCHAR(20)					NOT NULL COMMENT '아이디',
	PASSWORD					VARCHAR(255)				NOT NULL COMMENT '비밀번호',
	NAME						VARCHAR(12)					NOT NULL COMMENT '이름',
	EMAIL						VARCHAR(40)					NOT NULL COMMENT '이메일',
	LOGIN_TYPE					VARCHAR(5)					NOT NULL COMMENT '로그인 유형',
	ENABLED						TINYINT(1)					NOT NULL COMMENT '계정 활성화 (1:활성화/0:비활성화)',
	DELETE_YN					CHAR(1)						NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
	CREATED_AT					DATETIME					NOT NULL DEFAULT NOW() COMMENT '가입일자',
	UPDATED_AT					DATETIME					NOT NULL COMMENT '변경일자',
	PRIMARY KEY (USER_ID)
) ENGINE = INNODB DEFAULT CHARSET = UTF8MB4 COMMENT '사용자 테이블';


-- TABLE NAME : 사용자 권한 테이블 / USER_ROLES
CREATE TABLE USER_ROLES (
	USER_ID						BIGINT						NOT NULL COMMENT '사용자 번호',
	ROLE						VARCHAR(25)					NOT NULL COMMENT '권한 코드명',
	USE_YN						CHAR(1)						NOT NULL COMMENT '권한 사용 여부',
	DELETE_YN					CHAR(1)						NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
	CREATED_AT					DATETIME					NOT NULL DEFAULT NOW() COMMENT '권한 등록 일자',
	UPDATED_AT					DATETIME					NOT NULL COMMENT '권한 변경 일자',
	FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = INNODB DEFAULT CHARSET = UTF8MB4 COMMENT '사용자 권한 테이블';