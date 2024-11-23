package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private  Order order;

    private int orderPrice; // 주문 당시 가격

    private int count; // 주문 수량

    // == 생성 메서드 ==
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        /*쿠폰 등으로 인한 가격 변공이 있을 수 있기 때문에 orderPrice 따로 받음*/
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문 수량만큼 재고 수량 빼줌
        return orderItem;
    }

    // == 비즈니스 로직 ==
    public void cancel() {
        /**
         * 주문 취소 -> 재고 수량 늘려야됨
         * 아이템을 가져온 후 주문 수량만큼 addStock
         */
        getItem().addStock(count);
    }

    // == 조회 로직 ==
    public int getTotalPrice() {
        return getOrderPrice()*getCount();
    }
}
