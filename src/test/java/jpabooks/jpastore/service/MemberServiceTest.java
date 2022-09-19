package jpabooks.jpastore.service;

import jpabooks.jpastore.domain.Member;
import jpabooks.jpastore.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // 롤백 허용
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("김찬우");

        // when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member,memberRepository.findOne(savedId));

    }


    @Test()
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1= new Member();
        member1.setName("김찬우");

        Member member2= new Member();
        member2.setName("김찬우");

        // when

        memberService.join(member1);



        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());




        //then
    }

}