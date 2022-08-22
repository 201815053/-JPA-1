package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    //상품 리포지토리 개발
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null) {
            em.persist(item);
        } else {
            Item merge = em.merge(item);
            //update 비슷, 앞에 있는 Item은 영속성 콘텍스트에서 관리됨. 파라미터에서 넘어온 item은 관리안됨        }
        }
    }

    public Item findOne(Long id) { //단건
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { //여러개
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
