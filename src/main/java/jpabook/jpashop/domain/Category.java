package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="category_item",
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id")) // 다대일 -> 일대다, 다대일로 풀어내는 중간테이블
    private List<Item> items = new ArrayList<>();

    // 부모자식 관계(=자기 자긴과의 연관관계)
    // 다른 엔티티가 아닌 이름만 다른거라 똑같은 방식으로 해주면 됨
    @ManyToOne
    @JoinColumn(name="parent_id") // parent_id 컬럼에 상위 카테고리의 id가 저장됨
    private Category parent;

    @OneToMany(mappedBy = "parent") // child 필드가 parent 필드에 의해 매핑됨
    private List<Category> chile = new ArrayList<>();
}
