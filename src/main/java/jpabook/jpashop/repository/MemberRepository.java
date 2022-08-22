package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository //컴포넌트 스캔에 의해 자동으로 스프링 빈으로 관리됨
@RequiredArgsConstructor
public class MemberRepository {

    //final 사용시 원래는 @PersistenceContext 사용해야 인젝션 되지만 스프링 데이터 JPA가 있어서 @Autowired으로도 인젝션 가능
    private final EntityManager em; //entity manager 사용, Repository의 entity manager을 생성자로 인젝션함

//    @PersistenceUnit
//    private EntityManagerFactory emf; //manager factory

    public void save(Member member) { //회원정보 저장로직
       em.persist(member); //영속성컨텍스트에 멤버를 넣고 트랜잭션이 커밋되는 시점에 저장 후 인서트 쿼리 날라감
    }

    public Member findOne(Long id) { //회원정보 조회로직(단건조회)
        return em.find(Member.class, id);
    }

    public List<Member> findALL() { //회원정보 조회로직(리스트조회): 엔티티대상으로
        return em.createQuery("select m from Member m", Member.class) //sql: table 대상 <-> jpql: entity 대상
                .getResultList(); //결과반환
    }

    public List<Member> findByName(String name) { //파라미터 바인딩해서 특정 이름에 의한 회원 검색을 가능하게 하는 로직
        return em.createQuery("select m from Member m where m.name =:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
