package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded // 임베디드 내장 타입을 포함했다는 뜻
    private Address address;

    @OneToMany(mappedBy = "member") // 회원(1):주문(n)
    // mappedBy -> orders 테이블에 있는 member필드에 의해서 맵핑되었다는 뜻
    // 연관관계의 주인이 아니란 뜻
    private List<Order> orders = new ArrayList<>();
}
