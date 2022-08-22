package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //스프링 제공 - 쓸수있는 옵션이 더 많음, 조회(읽기) 성능 최적화
@RequiredArgsConstructor //롬복에서 사용하고 final 가지고 있는 필드만 생성자를 만들어줌
public class MemberService {

    //    @Autowired //스프링이 스프링빈에 등록되어있는 멤버리퍼지토리을 주입시켜줌
    //필드가 변경될 일 없을 것이므로 final 사용하고 컴파일 시점에 오류를 체크해줌
    // field injection:주입이 어려움 <-> setter injection: 주입이 쉬움, 보안은 안좋음
    //그래서 생성자 인젝션을 주로씀
    private final MemberRepository memberRepository;

//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
// 생성자에서 인젝션을 해줌, 한번 생성할 때 완성되기 때문에 중간에 멤버리퍼지토리가 변경 될 가능성 없음,테스트 케이스 작성에도 유용
//      }

    /**
     * 회원가입
     */
    @Transactional //변경, 쓰기할 땐 (readOnly = true) 쓰면 안됨
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member); //멤버를 저장
        return member.getId(); //아이디 반환
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers =
                memberRepository.findByName(member.getName()); //같은 이름이 있는지 조회
        if (!findMembers.isEmpty()) {
            //findMembers 가 조회되면 기존에 회원이 있다는 뜻으로 중복회원 발생 - 비어있어야 정상
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findALL();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId); //아이디를 하나만 조회(단건 조회)
    }
}