package com.studysiba.service.member;

import com.studysiba.common.DataConversion;
import com.studysiba.domain.member.MemberVO;
import com.studysiba.mapper.member.MemberMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Log4j
public class MemberServiceImpl implements MemberService{

    @Resource
    MemberMapper memberMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    /*
     *  회원초대장 메일발송
     */
    @Override
    public boolean inviteUser(String user) throws Exception {

        // 초대 여부 반환값
        boolean inviteState = false;

        MemberVO memberVO = new MemberVO();
        memberVO.setMbrId(user);

        int registrationStatus = memberMapper.registrationStatus(memberVO);
        log.info("가입상태확인 : " + registrationStatus);

        // 회원등록되어있을경우 ( 0:ERROR , 1:진행 )
        if ( registrationStatus == 1 ) {
            String checkStatus = memberMapper.getType(memberVO);
            log.info("회원타입확인 : " + checkStatus);

            // 회원가입 대기상태일경우 ( NONE )
            if ( checkStatus.equals("none") ) {

                // 인증코드 갱신
                memberVO.setMbrCode(DataConversion.returnUUID());
                memberMapper.renewAuthenticationCode(memberVO);
                MimeMessage mail = javaMailSender.createMimeMessage();

                // 이메일 & 코드 조회
                memberVO.setMbrEmail(memberMapper.getEmail(memberVO));
                memberVO.setMbrCode(memberMapper.getCode(memberVO));
                log.info("회원이메일확인 : " + memberVO.getMbrEmail());
                log.info("회원코드확인 : " + memberVO.getMbrCode());

                // 초대장 보내질 양식
                StringBuffer htmlStr = new StringBuffer();
                htmlStr.append("<a href='http://www.studysiba.com/member/mail/check");
                htmlStr.append("/"+memberVO.getMbrId());
                htmlStr.append("/"+memberVO.getMbrCode());
                htmlStr.append("'>");
                htmlStr.append("<img src='https://i.imgur.com/yr5w0qi.jpg' style='width:100%'></a><br/>");
                htmlStr.append("상단 초대장 클릭하시면 인증이 됩니다. 감사합니다^^");
                log.info("초대장 양식 : " + htmlStr.toString());

                // 초대장 제목
                mail.setSubject("[ 초대장발급 - " +  memberVO.getMbrId()+"님 ] 스터디시바");
                // 초대장 내용 타입
                mail.setText(htmlStr.toString(), "utf-8", "html");
                // 초대장 보내어질 이메일 주소
                mail.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(memberVO.getMbrEmail()));
                javaMailSender.send(mail);
                inviteState = true;
            }
        }
        return inviteState;
    }

}
