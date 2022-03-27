package jpabook.jpashop.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    
    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item);
        } else {
            /**
             * 1. 준영속 엔티티의 식별자 값으로 1차 캐시에서 엔티티를 조회한다. 없으면 DB에서 가져와서 1차 캐시에 저장한다.
             * 2. 준영속 엔티티의 값을 1차캐시에서 가져온 엔티티의 필드에 넣는다.
             * 3. 바꿔치기 된 1차 캐시에서 가져온 엔티티를 반환한다.
             */
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
