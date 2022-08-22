package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    //상품 서비스 개발
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

//    @Transactional
//    public Item updateItem(Long itemId, Book param){
//        Item findItem = itemRepository.findOne(itemId); //Id를 기반으로 실제 DB에 있는 영속성 엔티티를 찾아옴
//        findItem.setPrice(param.getPrice()); //findItem으로 찾아온 것은 영속상태임
//        findItem.setName(param.getName()); //값을 셋팅한 상태이기 때문에 끝나면 트랜잭션이 되면서 커밋됨
//        findItem.setStockQuantity(param.getStockQuantity());
//        return findItem;
//        //엔티티를 변경할 때는 merge말고 변경감지를 사용해야함 - merge는 값을 null로 받을 수 있어서 위험
//    }

    @Transactional //더 나은 코드, UpdateItemDto클래스를 만들어 이용해도됨
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

    }
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
