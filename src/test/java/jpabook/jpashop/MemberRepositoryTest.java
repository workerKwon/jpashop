package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional // 엔티티 매니저를 통한 모든 데이터 변경은 항상 트랜잭션 안에서 이루어져야한다. 그래서 트랜잭션이 필요하다.
    @Rollback(false) // 테스트가 끝나면 디비가 자동으로 롤백된다. 그것을 취소함
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 같은 엔티티로 인식한다. 같은 트랜잭션 안에서 저장하고 조회하면 1차 캐시에서 엔티티를 불러온다. 그래서 셀렉트 쿼리가 나가지도 않는다.
        Assertions.assertThat(findMember).isEqualTo(member); 
    }


}