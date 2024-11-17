package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    EntityManager em;
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}