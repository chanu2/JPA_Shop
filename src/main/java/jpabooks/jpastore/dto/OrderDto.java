package jpabooks.jpastore.dto;

import jpabooks.jpastore.domain.Address;
import jpabooks.jpastore.domain.Order;
import jpabooks.jpastore.domain.OrderItem;
import jpabooks.jpastore.domain.OrderStatus;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class OrderDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;   // dto안에는 엔티티가 있으면 안된다


    public OrderDto(Order order) {
        orderId = order.getId();
        name = order.getMember().getName();
        orderStatus = order.getStatus();
        orderDate = order.getOrderDate();
        address = order.getDelivery().getAddress();
        orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());

    }
}
