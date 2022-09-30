package jpabooks.jpastore.api;

import jpabooks.jpastore.domain.Order;
import jpabooks.jpastore.dto.SimpleOrderDto;
import jpabooks.jpastore.repository.OrderRepository;
import jpabooks.jpastore.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**  xtoOne 관계에서
 *  order
 *  order -> member     order만 끌고 오고 싶다
 *  order --> delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        return all;
    }

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2(){
        // order 2개
        //  N+1 --> 1+ 회원 N + 배송 N
        // 첫번쨰 쿼리에서 order 두개를 가져온다 n=2
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return result;
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3(){

        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream().map(o -> new SimpleOrderDto(o)).collect(Collectors.toList());
        return result;

    }

    //v4는 잘 사용하지 않지만 너무 많은 데이터 실시간 트레픽을 다룰 때는 고려해보는 것도 나쁘지 않다


}
