package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 한 테이블에 다 집어넣음
@DiscriminatorColumn(name="dtype")
public abstract class Item { // 구현체 가지고 갈 거라 추상 클래스로
    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;
}
