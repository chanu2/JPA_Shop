package jpabooks.jpastore.domain.item;

import jpabooks.jpastore.domain.Category;
import jpabooks.jpastore.exception.NotEnoughtStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  // 싱글 테이블 전략
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    //entity 안에 비지니스로직을 넣는게 좋다
    //==비지니스 로직 ==//

    /**
     * stock 증가  SETTER를 사용하여 변경하는 것이 아닌 따로 메서드를 만들어서 변경하는 것이 바람직하다
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughtStockException("need more stock");
        }
        this.stockQuantity = restStock;

    }



}
