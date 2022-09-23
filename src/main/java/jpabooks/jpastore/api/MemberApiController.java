package jpabooks.jpastore.api;

import jpabooks.jpastore.domain.Member;
import jpabooks.jpastore.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    //엔티티를 직접 반환하면 안된다
    @GetMapping("/api/v1/members")  //엔티티를 직접 노출 하게되면  엔티티에 있는 정보들이 다 노출된다 만약에 MEMBER 엔티티에 order에 @JsonIgnore을 사용하면 order은 끌고 오지 않지만 다른 곳에서는 order를 필요로 하고 이런거 하면 복잡해진다.
    public List<Member> memberV1(){
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream().map(m -> new MemberDto(m.getName())).collect(Collectors.toList());

        return new Result(collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto{  //이름만 넘기는 요구사항
        private String name;
    }
    //별도의 dto를 만들어서 파라미터로 받는 것이 좋다!! 엔티티를 직접 바인딩 받아서 사용하는 것은 좋은 방법이 아니다.
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }


    //Dto를 만들어서 사용한다
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid updateMemberRequest request){ // 없데이트할 이름이 넘어온다

        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);

        return new UpdateMemberResponse(findMember.getId(),findMember.getName());

    }

    @Data
    static class updateMemberRequest{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }
    @Data
    static class CreateMemberRequest{  //바꾸고 싶은것
        private String name;

    }

    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
