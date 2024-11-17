package service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    //@Autowired // 스프링이 스프링빈에 등록되어있는 멤버리포지토리 인젝션해줌 (field injection)
    private MemberRepository memberRepository;

    // 회원 가입
    @Transactional
    public Long join(Member member){
        // 중복 회원 검증 로직
        validateDuplicateMember(member);
        // 문제 없으면 다음 로직으로 넘어감
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        // EXCEPTION
        if(!findMembers.isEmpty()){ // 값이 있으면 -> 중복이라는 뜻
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 하나 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
