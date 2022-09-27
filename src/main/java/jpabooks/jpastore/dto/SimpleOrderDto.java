package jpabooks.jpastore.dto;

import jpabooks.jpastore.domain.Address;
import jpabooks.jpastore.domain.Order;
import jpabooks.jpastore.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleOrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order order) {   //Order order 이거는 상관없다

        orderId = order.getId();
        name = order.getMember().getName();  // Lazy가 초기화 된다
        orderDate = order.getOrderDate();
        orderStatus = order.getStatus();
        address = order.getDelivery().getAddress(); // Lazy가 초기화 된다
    }
}

