package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // jpa의 어떤 내장 타입이라는 뜻
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
