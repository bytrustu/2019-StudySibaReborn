-- 회원정보
CREATE TABLE `MEMBER` (
	`MBR_NO`      INT(10)     NOT NULL, -- 순번
	`MBR_TYPE`    VARCHAR(10) NOT NULL, -- 로그인타입
	`MBR_AUTH`    VARCHAR(10) NOT NULL, -- 권한
	`MBR_ID`      VARCHAR(50) NOT NULL, -- 아이디
	`MBR_PASS`    VARCHAR(50) NOT NULL, -- 비밀번호
	`MBR_NICK`    VARCHAR(50) NOT NULL, -- 닉네임
	`MBR_EMAIL`   VARCHAR(50) NOT NULL, -- 이메일
	`MBR_PROFILE` VARCHAR(50) NOT NULL, -- 프로필사진
	`MBR_CONNECT` TIMESTAMP   NOT NULL, -- 접속시간
	`MBR_JOIN`    TIMESTAMP   NOT NULL  -- 가입일자
);

-- 회원정보
ALTER TABLE `MEMBER`
	ADD CONSTRAINT `PK_MEMBER` -- 회원정보 기본키
		PRIMARY KEY (
			`MBR_ID` -- 아이디
		);

-- 방문자
CREATE TABLE `VISIT` (
	`VIS_NO`   INT(10)     NOT NULL, -- 순번
	`VIS_ID`   VARCHAR(50) NULL,     -- 아이디
	`VIS_DATE` TIMESTAMP   NOT NULL  -- 방문일자
);

-- 방문자
ALTER TABLE `VISIT`
	ADD CONSTRAINT `PK_VISIT` -- 방문자 기본키
		PRIMARY KEY (
			`VIS_NO` -- 순번
		);

-- 친구
CREATE TABLE `FRIEND` (
	`FRD_NO`      INT(10)     NOT NULL, -- 순번
	`FRD_ID`      VARCHAR(50) NOT NULL, -- 아이디
	`FRD_FID`     VARCHAR(50) NOT NULL, -- 친구아이디
	`FRD_REQUEST` INT(3)      NOT NULL  -- 요청
);

-- 친구
ALTER TABLE `FRIEND`
	ADD CONSTRAINT `PK_FRIEND` -- 친구 기본키
		PRIMARY KEY (
			`FRD_NO` -- 순번
		);

-- 메세지정보
CREATE TABLE `ROOM` (
	`ROO_NO`        INT(10)     NOT NULL, -- 방번호
	`ROO_FRS_USR`   VARCHAR(50) NOT NULL, -- 회원1
	`ROO_FRS_STATE` VARCHAR(50) NOT NULL, -- 회원2
	`ROO_SEC_USR`   TIMESTAMP   NOT NULL, -- 회원1체크
	`ROO_SEC_STATE` TIMESTAMP   NOT NULL  -- 회원2체크
);

-- 메세지정보
ALTER TABLE `ROOM`
	ADD CONSTRAINT `PK_ROOM` -- 메세지정보 기본키
		PRIMARY KEY (
			`ROO_NO` -- 방번호
		);

-- 메세지
CREATE TABLE `MESSAGE` (
	`MSG_NO`      INT(10)      NOT NULL, -- 순번
	`MSG_ROOM`    INT(10)      NOT NULL, -- 방번호
	`MSG_TYPE`    VARCHAR(20)  NOT NULL, -- 구분
	`MSG_FROM`    VARCHAR(50)  NOT NULL, -- 보낸사람
	`MSG_TO`      VARCHAR(50)  NOT NULL, -- 받는사람
	`MSG_TEXT`    VARCHAR(300) NOT NULL, -- 내용
	`MSG_RECEIVE` VARCHAR(50)  NOT NULL, -- 수신여부
	`MSG_DELETE`  VARCHAR(50)  NOT NULL, -- 삭제여부
	`MSG_DATE`    TIMESTAMP    NOT NULL  -- 보낸시각
);

-- 메세지
ALTER TABLE `MESSAGE`
	ADD CONSTRAINT `PK_MESSAGE` -- 메세지 기본키
		PRIMARY KEY (
			`MSG_NO` -- 순번
		);

-- 포인트
CREATE TABLE `POINT` (
	`PNT_NO`    INT(10)     NOT NULL, -- 순번
	`PNT_ID`    VARCHAR(50) NOT NULL, -- 아이디
	`PNT_SCORE` INT(10)     NOT NULL  -- 점수
);

-- 포인트
ALTER TABLE `POINT`
	ADD CONSTRAINT `PK_POINT` -- 포인트 기본키
		PRIMARY KEY (
			`PNT_NO` -- 순번
		);

-- 공통게시판
CREATE TABLE `BOARD` (
	`BRD_NO`        INT(10)       NOT NULL, -- 글번호
	`BRD_TYPE`      VARCHAR(20)   NULL,     -- 게시판구분
	`BRD_ID`        VARCHAR(50)   NULL,     -- 아이디
	`BRD_TITLE`     VARCHAR(100)  NULL,     -- 제목
	`BRD_CONTENT`   VARCHAR(2048) NULL,     -- 내용
	`BRD_GNO`       INT(10)       NULL,     -- 그룹번호
	`BRD_STEP`      INT(10)       NULL,     -- 분기번호
	`BRD_INDENT`    INT(10)       NULL,     -- 들여쓰기
	`BRD_COUNT`     INT(10)       NULL,     -- 조회수
	`BRD_AVAILABLE` INT(3)        NULL,     -- 삭제여부
	`BRD_DATE`      TIMESTAMP     NULL      -- 등록일자
);

-- 공통게시판
ALTER TABLE `BOARD`
	ADD CONSTRAINT `PK_BOARD` -- 공통게시판 기본키
		PRIMARY KEY (
			`BRD_NO` -- 글번호
		);

-- 공통좋아요
CREATE TABLE `LIKE` (
	`LIKE_NO`   INT(10)     NOT NULL, -- 순번
	`LIKE_FNO`  INT(10)     NOT NULL, -- 글번호
	`LIKE_ID`   VARCHAR(50) NOT NULL, -- 아이디
	`LIKE_DATE` TIMESTAMP   NOT NULL  -- 등록일자
);

-- 공통좋아요
ALTER TABLE `LIKE`
	ADD CONSTRAINT `PK_LIKE` -- 공통좋아요 기본키
		PRIMARY KEY (
			`LIKE_NO` -- 순번
		);

-- 공통댓글
CREATE TABLE `COMMENT` (
	`CMT_NO`        INT(10)     NOT NULL, -- 순번
	`CMT_BNO`       INT(10)     NOT NULL, -- 글번호
	`CMT_PRE`       VARCHAR(50) NULL,     -- 참조닉네임
	`CMT_ID`        VARCHAR(50) NOT NULL, -- 아이디
	`CMT_CONTENT`   VARCHAR(50) NOT NULL, -- 내용
	`CMT_GNO`       INT(10)     NOT NULL, -- 그룹번호
	`CMT_STEP`      INT(10)     NOT NULL, -- 분기번호
	`CMT_INDENT`    INT(10)     NOT NULL, -- 들여쓰기
	`CMT_AVAILABLE` INT(3)      NOT NULL, -- 삭제여부
	`CMT_DATE`      TIMESTAMP   NOT NULL  -- 등록일자
);

-- 공통댓글
ALTER TABLE `COMMENT`
	ADD CONSTRAINT `PK_COMMENT` -- 공통댓글 기본키
		PRIMARY KEY (
			`CMT_NO` -- 순번
		);

-- 로그
CREATE TABLE `DATALOG` (
	`DLG_NO`   INT(10)     NOT NULL, -- 순번
	`DLG_FNO`  INT(10)     NULL,     -- 참조번호
	`DLG_TYPE` VARCHAR(10) NULL,     -- 구분
	`DLG_ID`   VARCHAR(50) NULL,     -- 아이디
	`DLG_TEXT` VARCHAR(50) NULL,     -- 내용
	`DLG_DATE` TIMESTAMP   NULL      -- 등록일자
);

-- 로그
ALTER TABLE `DATALOG`
	ADD CONSTRAINT `PK_DATALOG` -- 로그 기본키
		PRIMARY KEY (
			`DLG_NO` -- 순번
		);

-- 파일업로드
CREATE TABLE `UPLOAD` (
	`ULD_NO`       INT(10)     NOT NULL, -- 순번
	`ULD_FNO`      INT(10)     NOT NULL, -- 참조번호
	`ULD_ID`       VARCHAR(50) NOT NULL, -- 아이디
	`ULD_TYPE`     VARCHAR(10) NOT NULL, -- 타입
	`ULD_TEXT`     VARCHAR(50) NOT NULL, -- 내용
	`ULD_UUID`     VARCHAR(50) NOT NULL, -- 랜덤값
	`ULD_FILENAME` VARCHAR(50) NOT NULL, -- 파일이름
	`ULD_DATE`     TIMESTAMP   NOT NULL  -- 등록일자
);

-- 파일업로드
ALTER TABLE `UPLOAD`
	ADD CONSTRAINT `PK_UPLOAD` -- 파일업로드 기본키
		PRIMARY KEY (
			`ULD_NO` -- 순번
		);

-- 스터디게시판
CREATE TABLE `STUDYBOARD` (
	`STD_NO`        INT(10)      NOT NULL, -- 번호
	`STD_ID`        VARCHAR(50)  NOT NULL, -- 아이디
	`STD_GROUP`     VARCHAR(20)  NOT NULL, -- 그룹이름
	`STD_DIVIDE`    VARCHAR(10)  NOT NULL, -- 구분
	`STD_AREA`      VARCHAR(20)  NOT NULL, -- 지역
	`STD_TITLE`     VARCHAR(100) NOT NULL, -- 제목
	`STD_CONTENT`   VARCHAR(500) NOT NULL, -- 내용
	`STD_START`     VARCHAR(20)  NOT NULL, -- 시작일
	`STD_END`       VARCHAR(20)  NOT NULL, -- 종료일
	`STD_LIMIT`     INT(3)       NOT NULL, -- 참여인원
	`STD_LAT`       VARCHAR(20)  NOT NULL, -- 위도
	`STD_LNG`       VARCHAR(20)  NOT NULL, -- 경도
	`STD_AVAILABLE` INT(3)       NOT NULL, -- 삭제여부
	`STD_UPDATE`    TIMESTAMP    NOT NULL, -- 갱신일자
	`STD_REG`       TIMESTAMP    NOT NULL  -- 등록일자
);

-- 스터디게시판
ALTER TABLE `STUDYBOARD`
	ADD CONSTRAINT `PK_STUDYBOARD` -- 스터디게시판 기본키
		PRIMARY KEY (
			`STD_NO` -- 번호
		);

-- 스터디그룹
CREATE TABLE `GROUP` (
	`GRP_NO`    INT(10)     NOT NULL, -- 그룹번호
	`GRP_ID`    VARCHAR(50) NOT NULL, -- 그룹장
	`GRP_GROUP` VARCHAR(50) NOT NULL, -- 그룹이름
	`GRP_DATE`  VARCHAR(50) NOT NULL  -- 개설일자
);

-- 스터디그룹
ALTER TABLE `GROUP`
	ADD CONSTRAINT `PK_GROUP` -- 스터디그룹 기본키
		PRIMARY KEY (
			`GRP_NO` -- 그룹번호
		);

-- 그룹메세지
CREATE TABLE `GROUPMESSAGE` (
	`GRM_NO`   INT(10)      NOT NULL, -- 순번
	`GRM_GNO`  INT(10)      NOT NULL, -- 그룹번호
	`GRM_ID`   VARCHAR(50)  NOT NULL, -- 아이디
	`GRM_TEXT` VARCHAR(500) NOT NULL, -- 내용
	`GRM_DATE` TIMESTAMP    NOT NULL  -- 등록일자
);

-- 그룹메세지
ALTER TABLE `GROUPMESSAGE`
	ADD CONSTRAINT `PK_GROUPMESSAGE` -- 그룹메세지 기본키
		PRIMARY KEY (
			`GRM_NO` -- 순번
		);

-- 참여회원
CREATE TABLE `GROUPMEMBER` (
	`GRM_NO`   INT(10)     NOT NULL, -- 순번
	`GRM_GNO`  INT(10)     NOT NULL, -- 그룹번호
	`GRM_ID`   VARCHAR(50) NOT NULL, -- 참여아이디
	`GRM_DATE` TIMESTAMP   NOT NULL  -- 참여일자
);

-- 참여회원
ALTER TABLE `GROUPMEMBER`
	ADD CONSTRAINT `PK_GROUPMEMBER` -- 참여회원 기본키
		PRIMARY KEY (
			`GRM_NO` -- 순번
		);

-- 방문자
ALTER TABLE `VISIT`
	ADD CONSTRAINT `FK_MEMBER_TO_VISIT` -- 회원정보 -> 방문자
		FOREIGN KEY (
			`VIS_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 친구
ALTER TABLE `FRIEND`
	ADD CONSTRAINT `FK_MEMBER_TO_FRIEND` -- 회원정보 -> 친구
		FOREIGN KEY (
			`FRD_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 친구
ALTER TABLE `FRIEND`
	ADD CONSTRAINT `FK_MEMBER_TO_FRIEND2` -- 회원정보 -> 친구2
		FOREIGN KEY (
			`FRD_FID` -- 친구아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 메세지정보
ALTER TABLE `ROOM`
	ADD CONSTRAINT `FK_MEMBER_TO_ROOM` -- 회원정보 -> 메세지정보
		FOREIGN KEY (
			`ROO_FRS_USR` -- 회원1
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 메세지정보
ALTER TABLE `ROOM`
	ADD CONSTRAINT `FK_MEMBER_TO_ROOM2` -- 회원정보 -> 메세지정보2
		FOREIGN KEY (
			`ROO_FRS_STATE` -- 회원2
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 메세지
ALTER TABLE `MESSAGE`
	ADD CONSTRAINT `FK_ROOM_TO_MESSAGE` -- 메세지정보 -> 메세지
		FOREIGN KEY (
			`MSG_ROOM` -- 방번호
		)
		REFERENCES `ROOM` ( -- 메세지정보
			`ROO_NO` -- 방번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 메세지
ALTER TABLE `MESSAGE`
	ADD CONSTRAINT `FK_MEMBER_TO_MESSAGE` -- 회원정보 -> 메세지
		FOREIGN KEY (
			`MSG_FROM` -- 보낸사람
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 메세지
ALTER TABLE `MESSAGE`
	ADD CONSTRAINT `FK_MEMBER_TO_MESSAGE2` -- 회원정보 -> 메세지2
		FOREIGN KEY (
			`MSG_TO` -- 받는사람
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 포인트
ALTER TABLE `POINT`
	ADD CONSTRAINT `FK_MEMBER_TO_POINT` -- 회원정보 -> 포인트
		FOREIGN KEY (
			`PNT_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 공통게시판
ALTER TABLE `BOARD`
	ADD CONSTRAINT `FK_MEMBER_TO_BOARD` -- 회원정보 -> 공통게시판
		FOREIGN KEY (
			`BRD_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		);

-- 공통좋아요
ALTER TABLE `LIKE`
	ADD CONSTRAINT `FK_BOARD_TO_LIKE` -- 공통게시판 -> 공통좋아요
		FOREIGN KEY (
			`LIKE_FNO` -- 글번호
		)
		REFERENCES `BOARD` ( -- 공통게시판
			`BRD_NO` -- 글번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 공통댓글
ALTER TABLE `COMMENT`
	ADD CONSTRAINT `FK_BOARD_TO_COMMENT` -- 공통게시판 -> 공통댓글
		FOREIGN KEY (
			`CMT_BNO` -- 글번호
		)
		REFERENCES `BOARD` ( -- 공통게시판
			`BRD_NO` -- 글번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 로그
ALTER TABLE `DATALOG`
	ADD CONSTRAINT `FK_MEMBER_TO_DATALOG` -- 회원정보 -> 로그
		FOREIGN KEY (
			`DLG_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 로그
ALTER TABLE `DATALOG`
	ADD CONSTRAINT `FK_BOARD_TO_DATALOG` -- 공통게시판 -> 로그
		FOREIGN KEY (
			`DLG_FNO` -- 참조번호
		)
		REFERENCES `BOARD` ( -- 공통게시판
			`BRD_NO` -- 글번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 로그
ALTER TABLE `DATALOG`
	ADD CONSTRAINT `FK_MESSAGE_TO_DATALOG` -- 메세지 -> 로그
		FOREIGN KEY (
			`DLG_FNO` -- 참조번호
		)
		REFERENCES `MESSAGE` ( -- 메세지
			`MSG_NO` -- 순번
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 로그
ALTER TABLE `DATALOG`
	ADD CONSTRAINT `FK_COMMENT_TO_DATALOG` -- 공통댓글 -> 로그
		FOREIGN KEY (
			`DLG_FNO` -- 참조번호
		)
		REFERENCES `COMMENT` ( -- 공통댓글
			`CMT_NO` -- 순번
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 로그
ALTER TABLE `DATALOG`
	ADD CONSTRAINT `FK_STUDYBOARD_TO_DATALOG` -- 스터디게시판 -> 로그
		FOREIGN KEY (
			`DLG_FNO` -- 참조번호
		)
		REFERENCES `STUDYBOARD` ( -- 스터디게시판
			`STD_NO` -- 번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 파일업로드
ALTER TABLE `UPLOAD`
	ADD CONSTRAINT `FK_BOARD_TO_UPLOAD` -- 공통게시판 -> 파일업로드
		FOREIGN KEY (
			`ULD_FNO` -- 참조번호
		)
		REFERENCES `BOARD` ( -- 공통게시판
			`BRD_NO` -- 글번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 파일업로드
ALTER TABLE `UPLOAD`
	ADD CONSTRAINT `FK_MESSAGE_TO_UPLOAD` -- 메세지 -> 파일업로드
		FOREIGN KEY (
			`ULD_FNO` -- 참조번호
		)
		REFERENCES `MESSAGE` ( -- 메세지
			`MSG_NO` -- 순번
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 파일업로드
ALTER TABLE `UPLOAD`
	ADD CONSTRAINT `FK_STUDYBOARD_TO_UPLOAD` -- 스터디게시판 -> 파일업로드
		FOREIGN KEY (
			`ULD_FNO` -- 참조번호
		)
		REFERENCES `STUDYBOARD` ( -- 스터디게시판
			`STD_NO` -- 번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 파일업로드
ALTER TABLE `UPLOAD`
	ADD CONSTRAINT `FK_GROUP_TO_UPLOAD` -- 스터디그룹 -> 파일업로드
		FOREIGN KEY (
			`ULD_FNO` -- 참조번호
		)
		REFERENCES `GROUP` ( -- 스터디그룹
			`GRP_NO` -- 그룹번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 파일업로드
ALTER TABLE `UPLOAD`
	ADD CONSTRAINT `FK_MEMBER_TO_UPLOAD` -- 회원정보 -> 파일업로드
		FOREIGN KEY (
			`ULD_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 스터디게시판
ALTER TABLE `STUDYBOARD`
	ADD CONSTRAINT `FK_MEMBER_TO_STUDYBOARD` -- 회원정보 -> 스터디게시판
		FOREIGN KEY (
			`STD_ID` -- 아이디
		)
		REFERENCES `MEMBER` ( -- 회원정보
			`MBR_ID` -- 아이디
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 스터디그룹
ALTER TABLE `GROUP`
	ADD CONSTRAINT `FK_STUDYBOARD_TO_GROUP` -- 스터디게시판 -> 스터디그룹
		FOREIGN KEY (
			`GRP_NO` -- 그룹번호
		)
		REFERENCES `STUDYBOARD` ( -- 스터디게시판
			`STD_NO` -- 번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 그룹메세지
ALTER TABLE `GROUPMESSAGE`
	ADD CONSTRAINT `FK_GROUP_TO_GROUPMESSAGE` -- 스터디그룹 -> 그룹메세지
		FOREIGN KEY (
			`GRM_GNO` -- 그룹번호
		)
		REFERENCES `GROUP` ( -- 스터디그룹
			`GRP_NO` -- 그룹번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;

-- 참여회원
ALTER TABLE `GROUPMEMBER`
	ADD CONSTRAINT `FK_GROUP_TO_GROUPMEMBER` -- 스터디그룹 -> 참여회원
		FOREIGN KEY (
			`GRM_GNO` -- 그룹번호
		)
		REFERENCES `GROUP` ( -- 스터디그룹
			`GRP_NO` -- 그룹번호
		)
		ON DELETE CASCADE
		ON UPDATE CASCADE;