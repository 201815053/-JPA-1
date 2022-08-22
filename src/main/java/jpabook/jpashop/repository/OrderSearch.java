package jpabook.jpashop.repository;


import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

//검색 조건 파라미터
@Getter @Setter
public class OrderSearch {

//    public static boolean getOrderstatus;
    private String memberName; //회원 이름
    private OrderStatus orderStatus; // 주문 상태[ORDER, CANCEL]
}
