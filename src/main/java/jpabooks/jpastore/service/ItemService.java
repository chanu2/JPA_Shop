package jpabooks.jpastore.service;

import jpabooks.jpastore.domain.item.Book;
import jpabooks.jpastore.domain.item.Item;
import jpabooks.jpastore.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)  //읽기
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;


    @Transactional  //변경
    public void saveItem(Item item){
        itemRepository.save(item);

    }
    @Transactional  // (수정)업데이트
    public void updateItem(Long itemId,String name, int price, int stockQuantity){ //변경감지
        Item findItem = itemRepository.findOne(itemId); //영속상태가 된다 값을 세팅하면 transactional에서 커밋이됨 flush해주고 바뀐 사항들을 쿼리로 날려준다
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);  // 되도록 setter를 사용하지 말고 change같은 메서드를 만들어서 사용하는 것을 추천한다 유지보수에 좋다

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
