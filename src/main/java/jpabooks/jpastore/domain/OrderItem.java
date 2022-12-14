package jpabooks.jpastore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabooks.jpastore.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int OrderPrice;

    private int count;

    //==생성 매서드==//
    public static OrderItem createOrderItem(Item item,int orderPrice,int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    //==비지니스 로직 ==//
    public void cancel() {
        getItem().addStock(count);
    }


    // git bash
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
