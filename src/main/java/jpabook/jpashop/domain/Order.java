package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // order by의 order와 겹쳐서
@Getter @Setter
public class Order {
    @Id @GeneratedValue
    @Column(name="order_id")
    private Long Id;

    @ManyToOne(fetch=FetchType.LAZY) // 주문(n):회원(1)
    @JoinColumn(name="member_id") // fk 설정
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // 오더 아이템스에 오더를 저장하면 오더 아이템스도 같이 저장됨
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // 오더를 저장할 때 딜리버리 엔티티도 같이 persist 해줌
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 그냥 Date 쓰면 어노테이션 매핑 필요, 이거는 hibernate가 알아서

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [Order, Cancel]

    // ==== 연관 관계 메서드 ====
    // 컨트롤 하는 쪽에 작성해주는 게 좋음
    // 로직 상에서 양쪽에 값을 세팅해주기 위함
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);

        // member.getOrders().add(order);
        // order.setMember(member);
        // 를
        // order.setMember(member);
        // 로 줄어들게 함
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
