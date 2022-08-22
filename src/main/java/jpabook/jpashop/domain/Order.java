package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") //연관관계 주인(FK)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
//
//    persist(orderItemA) ->  cascade 추가후
//    persist(orderItemA)     persist(order)
//    persist(orderItemB)
//    persist(orderItemC)     persist(order)
//    persist(order) :원래 엔티티당 각자 persist를 호출해야하는데,cascade를 호출하면 order만 호출, 삭제(ALL 경우)해도 다같이 된다.


    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id") //연관관계 주인(FK)
    private Delivery delivery;

    private LocalDateTime orderDate; //주문시간 -시간분

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드 - 주문, 주문 상품 엔티티 개발==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){ //OrderItem 여러개 넘길 수 있게 해줌
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비즈니스 로직==//
    /**
     *  주문 취소 - 주문을 cancel하면 재고수량도 증가해야함
     */

    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) { //예외 상황
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL); //상태 변경, 트랜잭션 커밋시점에 변경사항 찾아 db에 업데이트 문 날리고 트랜잭션을 커밋한다.(플러쉬 할 때 더티체킹)
        for(OrderItem orderItem : orderItems) { //루프를 돌며 orderItem 에서 취소가 되면 아이템 재고 수량을 다시 원복 시킴
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     *  전체 주문 가격 조회 - 최종 결과
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
        /*       return orderItems.stream()
         *               .mapToInt(OrderItem::getTotalPrice)
         *               .sum(); // 위에 코드를 이 코드로 단순화 시킬 수 있다.
         */
    }


}