package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // 컴포넌트 스캔에 의해 스프링빈으로 관리됨
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext // JPA가 제공하는 표준
    private final EntityManager em; // 스프링이 엔티티 매니저 만들어서 주입해줌(인젝션)

    // 회원 저장
    public void save(Member member){
        em.persist(member);
    }

    // 회원 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    // 회원 리스트 조회
    public List<Member> findAll(){
        // (JPQL, 반환 타입)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 회원 이름으로 조회
    public List<Member> findByName(String name){
        // 쿼리문 파라미터 바인딩하려면 앞에 콜론(:)
        // 이후 setParameter(쿼리문에 :붙은 부분, 값)
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
