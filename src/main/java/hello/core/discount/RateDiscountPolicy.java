package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component //애노테이션이 붙은 모든 클래스를 스캔하여 스프링빈 자동등록 / 뺄꺼지정 (이유는 Appconfig에 수동으로 등록된것이 있기때문)//
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
