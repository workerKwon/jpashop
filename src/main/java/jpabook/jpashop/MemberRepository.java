package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository // 엔티티를 찾아주는 객체
public class MemberRepository { 

    @PersistenceContext // 스프링 부트가 엔티티 매니저를 주입해주는 어노테이션
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
