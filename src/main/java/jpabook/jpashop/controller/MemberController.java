package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new") //회원 가입
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm()); //controller -> view로 넘어갈 때 데이터를 실어 보냄
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){ //@vaild 기능을 이용해 어노테이션 기능 사용가능

        if(result.hasErrors()){ //NotEmpty error:회원명을 제대로 기입을 안하면 발생하는 에러
            return "members/createMemberForm"; //다시 회원가입 화면으로 돌아감
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member); //호출하면 저장됨
        return "redirect:/"; //저장되고 리로딩되면 안좋기 때문에 redirect로 form에 보냄
    }

    @GetMapping("/members") //회원 목록
    public String list(Model model){
        List<Member> members = memberService.findMembers(); //모든 멤버를 조회한 후
        model.addAttribute("members", members); //모델에 담아서 화면에 넘김
        return "members/memberList"; //html에서는 루프를 돌면서 쭉 뿌림
    }

}
