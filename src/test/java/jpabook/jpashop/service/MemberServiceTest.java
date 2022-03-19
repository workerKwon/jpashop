package jpabook.jpashop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        // given : 이런게 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        // when : 이런게 실행되면
        Long savedId = memberService.join(member);

        // then : 이런 결과가 나와야 한다.
        Assertions.assertEquals(member, memberRepository.findOne(savedId));

    }

    @Test
    public void 회원_중복_예외() {
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2), "예외가 발생해야 한다.");

    }

}
