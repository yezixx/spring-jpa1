package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // === 비즈니스 로직 ===
    // 재고 수량 증가
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    // 재고 수량 감소
    public void removeQuantity(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}
