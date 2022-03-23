package jpabook.jpashop.service;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired 
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문() throws Exception {
        // given
        Member member = createMember();

        Item book = createBook("JPA Book", 10000, 5);

        // when
        Long orderId = orderService.order(member.getId(), book.getId(), 3);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "주문 상태가 ORDER");
        Assertions.assertEquals(1, getOrder.getOrderItems().size(), "주문 상품의 종류 수가 1개");
        Assertions.assertEquals(10000 * 3, getOrder.getTotalPrice(), "주문의 총 가격");
        Assertions.assertEquals(2, book.getStockQuantity(), "재고 개수");
    }

    @Test
    void 주문취소() throws Exception {
        // given
        Member member = createMember();
        Item book = createBook("JPA Book", 10000, 10);

        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        Assertions.assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL");
        Assertions.assertEquals(10, book.getStockQuantity(), "주문 취소된 상품은 재고가 다시 증가해야 한다.");
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember();
        Item item = createBook("JPA Book", 10000, 5);

        // when, then
        Assertions.assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), 11));
        Assertions.assertEquals(5, item.getStockQuantity(), "재고가 그대로 남아있어야 한다.");
    }

    private Item createBook(String name, int i, int count) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(i);
        book.setStockQuantity(count);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("kwontaeil");
        member.setAddress(new Address("seoul", "yuck-sam", "743-22"));
        em.persist(member);
        return member;
    }
}
