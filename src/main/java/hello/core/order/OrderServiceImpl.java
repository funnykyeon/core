package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // final이 붙은 객체의 생성자를 만들어준다.
public class OrderServiceImpl implements OrderService {

    //final
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;


    /** 의존관계 자동주입 - 필드주입 (사용하지 않는게 좋다.)
    @Autowired private MemberRepository memberRepository;
    @Autowired private DiscountPolicy discountPolicy;
    **/


    /** 의존관게 자동주입 - 수정자 주입(setter 주입, 생성자주입과 동시에 가능 )
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
    **/

    /** 의존관계 자동주입 - 메서드 주입
     @Autowired
     public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
     this.memberRepository = memberRepository;
     this.discountPolicy = discountPolicy;
     }
    **/


// 의존관계주입 - 생성자 주입 (생성자가 개일땐 Autowired 생략), (불변이며 누락방지)

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
