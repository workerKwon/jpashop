package jpabook.jpashop.service;

import java.util.List;

import jpabook.jpashop.domain.item.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 변경감지를 이용한 데이터 수정 방법
     * entityManager로 엔티티를 영속성 컨텍스트가 관리하도록 DB에서 가져오고,
     * 가져온 엔티티에 값을 넣으면 Transactional에 의해 트랜잭션이 끝날때 커밋이 되면서 값이 DB에 들어간다.
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }
}
