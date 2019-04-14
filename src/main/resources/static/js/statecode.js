// 공통상태코드
const stateCode = new Map();

/// 회원가입/수정
stateCode.set('MEMBER_STATE_SUCCESS', '회원정보가 등록되었습니다.');
stateCode.set('MEMBER_UPDATE_SUCCESS', '회원정보가 수정되었습니다.');
stateCode.set('ID_STATE_EMPTY', '아이디를 입력해주세요..');
stateCode.set('PASS_STATE_EMPTY', '비밀번호를 입력해주세요.');
stateCode.set('NICK_STATE_EMPTY', '닉네임이 입력해주세요.');
stateCode.set('EMAIL_STATE_EMPTY', '이메일을 입력해주세요.');
stateCode.set('PROFILE_STATE_EMPTY', '프로필사진을 설정해주세요.');
stateCode.set('ID_STATE_USED', '이미 사용중인 아이디 입니다.');
stateCode.set('EMAIL_STATE_USED', '이미 사용중인 이메일 입니다.');
stateCode.set('NICK_STATE_USED', '이미 사용중인 닉네임 입니다.');
stateCode.set('ID_STATE_ERROR', '부적절한 아이디 입니다.');
stateCode.set('PASS_STATE_ERROR', '부적절한 비밀번호 입니다.');
stateCode.set('NICK_STATE_ERROR', '부적절한 닉네임 입니다.');
stateCode.set('EMAIL_STATE_ERROR', '부적절한 이메일 입니다.');
stateCode.set('PROFILE_STATE_ERROR', '부적절한 프로필사진 입니다.');

// 초대장
stateCode.set('INVITE_STATE_SUCCESS', '초대장이 발송되었습니다.');
stateCode.set('INVITE_STATE_ERROR', '초대장 발송을 실패했습니다.');
stateCode.set('AUTH_STATE_SUCCESS', '초대장 인증에 성공했습니다.');
stateCode.set('AUTH_STATE_ERROR', '초대장 인증에 실패했습니다.');
stateCode.set('ID_STATE_WAITAPPROVAL', '이메일 승인대기 아이디 입니다.');

// 로그인
stateCode.set('LOGIN_STATE_SUCCESS', '로그인 되었습니다.');
stateCode.set('LOGIN_STATE_ERROR', '아이디 혹은 패스워드가 다릅니다.');

// 소셜 로그인
stateCode.set("SOCIAL_LOGIN_SUCCESS", "로그인 되었습니다.");
stateCode.set("SOCIAL_JOIN_SUCCESS", "회원가입 되었습니다.");
stateCode.set("SOCIAL_JOIN_ERROR", "회원가입 실패했습니다.");

// 미인증 회원정보삭제
stateCode.set('INFODEL_STATE_SUCCESS', '회원님의 정보를 삭제 했습니다.');
stateCode.set('INFODEL_STATE_ERROR', '회원님의 정보 삭제에 실패했습니다.');

// 패스워드 재설정 인증메일
stateCode.set('PASSMAIL_STATE_SUCCESS', '메일을 발송 했습니다.');
stateCode.set('PASSMAIL_STATE_ERROR', '이메일이 올바르지 않습니다.');
stateCode.set('PASSAUTH_STATE_SUCCESS', '메일 인증에 성공 했습니다.');
stateCode.set('PASSAUTH_STATE_ERROR', '메일 인증에 실패 했습니다.');

// 패스워드 변경
stateCode.set('PASS_CHANGE_SUCCESS', '비밀번호가 변경되었습니다..');
stateCode.set('PASS_CHANGE_ERROR', '비밀번호가 올바르지 않습니다.');

// 회원정보 변경
stateCode.set("INFORMATION_CHANGE_SUCCESS", "회원정보를 변경 했습니다.");
stateCode.set("INFORMATION_CHANGE_ERROR", "정보변경에 실패 했습니다.");
stateCode.set("SOCIAL_PASSWORD_ERROR", "소셜회원은 변경 할수 없습니다.");

// 로그아웃
stateCode.set("LOGOUT_STATE_SUCCESS", "로그아웃 되었습니다.");
stateCode.set("LOGOUT_STATE_ERROR", "잘못된 접근입니다.");

// 공지사항/커뮤니티 게시글
stateCode.set("BOARD_READ_SUCCESS","게시글을 조회 했습니다.");
stateCode.set("BOARD_READ_ERROR","잘못된 접근 입니다.");
stateCode.set("BOARD_WRITE_SUCCESS","게시글이 등록되었습니다.");
stateCode.set("BOARD_WRITE_ERROR","게시글 등록에 실패했습니다.");
stateCode.set("BOARD_UPDATE_SUCCESS","게시글이 수정되었습니다.");
stateCode.set("BOARD_UPDATE_ERROR","게시글 수정에 실패했습니다.");
stateCode.set("BOARD_DELETE_SUCCESS","게시글이 삭제되었습니다.");
stateCode.set("BOARD_DELETE_ERROR","게시글 삭제에 실패했습니다.");

// 공지사항/커뮤니티 추천
stateCode.set("LIKE_STATE_SUCCESS", "추천 등록되었습니다.");
stateCode.set("LIKE_STATE_ERROR", "추천 실패했습니다.");
stateCode.set("LIKE_STATE_ALREADY", "이미 추천 하였습니다.");

// 공지사항/커뮤니티 댓글
stateCode.set("COMMENT_WRITE_SUCCESS","댓글이 등록되었습니다.");
stateCode.set("COMMENT_WRITE_ERROR","댓글 등록에 실패했습니다.");
stateCode.set("COMMENT_UPDATE_SUCCESS","댓글이 수정되었습니다.");
stateCode.set("COMMENT_UPDATE_ERROR","댓글 수정에 실패했습니다.");
stateCode.set("COMMENT_DELETE_SUCCESS","댓글이 삭제되었습니다.");
stateCode.set("COMMENT_DELETE_ERROR","댓글 삭제에 실패했습니다.");

// 스터디참여 등록
stateCode.set("STUDY_REGISTER_SUCCESS","스터디가 등록되었습니다.");
stateCode.set("STUDY_REGISTER_ERROR","스터디 등록에 실패했습니다.");
stateCode.set("UPLOAD_STATE_SUCCESS","파일이 업로드 되었습니다.");
stateCode.set("UPLOAD_STATE_ERROR","파일 업로드에 실패했습니다.");

// 스터디참여 수정
stateCode.set("STUDY_UPDATE_SUCCESS","스터디가 수정 되었습니다.");
stateCode.set("STUDY_UPDATE_ERROR","스터디 수정에 실패했습니다.");

// 스터디참여 활성/비활성화
stateCode.set("STUDY_DELETE_SUCCESS","스터디가 비활성화 되었습니다..");
stateCode.set("STUDY_DELETE_ERROR","스터디 비활성화에 실패했습니다.");
stateCode.set("STUDY_ACTIVE_SUCCESS","스터디가 활성화 되었습니다.");
stateCode.set("STUDY_ACTIVE_ERROR","스터디 활성화에 실패했습니다.");

// 스터디참여 참여
stateCode.set("STUDY_JOIN_SUCCESS","스터디에 참여 했습니다.");
stateCode.set("STUDY_JOIN_ERROR","스터디 참여에 실패 했습니다.");
stateCode.set("STUDY_OUT_SUCCESS","스터디를 탈퇴 했습니다.");
stateCode.set("STUDY_OUT_ERROR","스터디 탈퇴에 실패 했습니다.");
stateCode.set("STUDY_STATE_ALREADY","이미 스터디에 참여중 입니다.");

// 스터디참여 최신 갱신
stateCode.set("STUDY_LATEST_SUCCESS","스터디가 최신글로 갱신 되었습니다.");
stateCode.set("STUDY_LATEST_ERROR","스터디 최신화에 실패 했습니다..");

// 스터디그룹 공지사항
stateCode.set("NOTICE_WRITE_SUCCESS","공지사항이 등록 되었습니다.");
stateCode.set("NOTICE_WRITE_ERROR","공지사항 등록에 실패 했습니다.");
stateCode.set("NOTICE_UPDATE_SUCCESS","공지사항 수정에 성공 했습니다.");
stateCode.set("NOTICE_UPDATE_ERROR","공지사항 수정에 실패 했습니다.");

// 스터디그룹 그룹탈퇴
stateCode.set("GROUP_OUT_SUCCESS","그룹에서 탈퇴 되었습니다.");
stateCode.set("GROUP_OUT_ERROR","그룹탈퇴에 실패 했습니다.");

// 스터디그룹/관리자페이지 권한
stateCode.set("GROUP_LOCATION_ERROR","스터디그룹 접근권한이 없습니다.");
stateCode.set("ADMIN_LOCATION_ERROR","관리자 권한이 없습니다.");

//
stateCode.set("MESSENGER_FIND_SUCCESS","님과 연결중 입니다.");
stateCode.set("MESSENGER_ME_ERROR","자신과 연결 할 수 없습니다.");
stateCode.set("MESSENGER_FIND_ERROR","존재하지 않는 회원 입니다.");
stateCode.set("MESSENGER_AUTH_ERROR", "권한이 없습니다.");

// 포인트
stateCode.set("POINT_CREATE_SUCCESS", "포인트가 생성되었습니다.");
stateCode.set("POINT_CREATE_ERROR", "포인트 생성에 실패했습니다..");
stateCode.set("POINT_UPDATE_SUCCESS", "포인트가 수정되었습니다.");
stateCode.set("POINT_UPDATE_ERROR", "포인트 수정에 실패했습니다..");

// 에러코드
stateCode.set("ERROR_404", "잘못된 접근 입니다.");
stateCode.set("ERROR_500", "잘못된 요청 입니다.");
stateCode.set("ERROR_400", "잘못된 요청 입니다.");
stateCode.set("ERROR_OTHERS", "잘못된 요청 입니다.");