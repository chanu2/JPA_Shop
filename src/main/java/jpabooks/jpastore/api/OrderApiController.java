package jpabooks.jpastore.api;


import jpabooks.jpastore.domain.Order;
import jpabooks.jpastore.domain.OrderItem;
import jpabooks.jpastore.dto.OrderDto;
import jpabooks.jpastore.repository.OrderRepository;
import jpabooks.jpastore.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/orders")   //엔티티 직접노출
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }


    @GetMapping("/api/v2/orders")   //엔티티 직접노출
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<OrderDto> collect = orders.stream().map(o -> new OrderDto(o)).collect(toList());

        return collect;
    }


    @GetMapping("/api/v3/orders")
    public List<OrderDto> orderV3(){
        List<Order> orders = orderRepository.findAllWithItem();
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());

        return result;
    }

    @GetMapping("/api/v3.1/orders")   // 이것을 먼저 사용하자 패이징 처리도 가능 하기 때문이다
    public List<OrderDto> orderV3_page(
            @RequestParam(value = "offset",defaultValue = "0") int offset,
                    @RequestParam(value = "limit",defaultValue = "100") int limit)
    {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);  // order에 걸린 xtoone의 관계는 다 fetch join으로 가져온다 (db에 뻥튀기가 안되기 때문에)
        List<OrderDto> result = orders.stream().map(o -> new OrderDto(o)).collect(toList());

        return result;
    }





}
