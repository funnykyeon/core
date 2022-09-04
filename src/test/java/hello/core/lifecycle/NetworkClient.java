package hello.core.lifecycle;

// 스프링 빈의 이벤트 라이프사이클 ( 싱클톤 )
// 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존고나계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
// 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 중비이 완료된 후 호출
// 소멸전 콜백 : 빈이 소멸되기 직전의 호출

// 스프링은 크게 3가지 방법으로 빈 생명주기 콜백을 지원한다.
// 1. 인터페이스(InitializingBean, DisposableBean)
//  - 스프링 전용 인터페이스에 의존, 초기화,소멸 메서드 이름변경불가, 코드를 고칠 수 없는 외부 라이브러리 에 적용 불가
//  - 거의 사용하지않는다.



// 2. 설정 정보에 초기화 메서드, 종료메서드 지정 @Bean(initMethod = "init", destroyMethod = "close")
//  - 메서드 이름이 자유롭다.
//  - 스프링 빈이 스프링 코드에 의존하지 않는다.
//  - 코드가 아니라 설정 정보를 사용하기 떄문에 코드를 고칠수 없는 외부 라이브러리에도 초기화,종료 메서드를 적용할 수 있다.
//  - @Bean의 destroyMethod는 기본값은 추론으로 등록되어있다 -> 추론기능은 close, shutdown 이라는 이름의 메서드를 자동으로 추론해서 호출한다.


// 3. PostConstruct, @PreDestroy 애노테이션 지원
//  - 최신 스프링에서 권장하는 방법
//  - 애노테이션하나로 매우편리
//  - 자바 표준이라 스프링 종속적인 기술이 아니다. 다른컨테이너에서도 동작한다.
//  - 컴포넌트 스캔과 어울린다.
//  - 유일한단점은 외부라이브러리에 적용 불가 -> 대체 initMethod 로 이

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);

    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    //서비스 시작시 호출
    public void connect() {
        System.out.println("connect = " + url);
    }
    
    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("close = " + url);
    }

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
