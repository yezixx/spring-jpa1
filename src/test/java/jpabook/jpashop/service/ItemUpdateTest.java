package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    @Test
    public void update() throws Exception{
        Book book = em.find(Book.class, 1L);

        // 트랜잭션
        book.setName("adfas");

        // 변경 감지 == dirty checking
        // 변경한 부분 찾아서 업데이트 쿼리를 자동으로 생성해서 db에 반영해줌 => 기본 메커니즘
        // 트랜잭션 커밋

    }
}
