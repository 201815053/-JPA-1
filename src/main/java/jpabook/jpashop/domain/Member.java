package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "user")
@Getter @Setter //lombok으로 getter, setter 받아옴
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private  Long id;
    private String name;

    @Embedded //내장 타입을 포함했다
    private  Address address;

    @OneToMany(mappedBy =  "member")
    private List<Order> orders = new ArrayList<>();


}
