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

    // ======= 생성 메서드 =======
    /*
    * 밖에서 오더를 set하는 방식이 아니라 생성을 할 때부터 createOrder를 무조건 호출해야 됨
    * 주문 생성과 관련된 비즈니스 로직은 여기에 모아둠
    * */
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem:orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); // 처음 상태로 강제
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // ======= 비즈니스 로직 =======
    /**
     * 주문 취소
     */
    public void cancel() {
        if(delivery.getStatus() == DeliveryStatus.COMP) { // 배송이 완료된 상품이면
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL); // 주문 상태 변경
        for (OrderItem orderItem: orderItems){ // 주문한 상품마다 취소 -> 재고 원상복구
            orderItem.cancel();
        }
    }

    // == 조회 로직 ==
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        int totalPrice=0;
        for (OrderItem orderItem:orderItems){
            totalPrice += orderItem.getTotalPrice(); // += 주문 수량 * 주문 금액
        }
        return totalPrice;
        /*alt+enterfh stream으로 바꿀 수도 있음*/
        /*reutnr orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();*/
    }

}
