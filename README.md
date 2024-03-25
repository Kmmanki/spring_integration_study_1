# Integration

# Main Component

스프링 인테그레이션은 파이프 필터 모델로 작성되며 구성은 **“파이프”, “필터”, “메시지”**로 이루어져 있다.

---

## Message

스프링 인테그레이션에서 사용되는 데이터를 의미하며 사용하는 데이터를 감싼 **래퍼(wrapper)**이다.
즉 Message<User>와 같은 wrapper형식으로 사용한다.

---

## Message Channel(Pipe)

메세지 채널은 **Pipe**의 역할을 하며 Message를 Filter로 전달하는 역할을 한다.
메시지를 전달하는 **프로듀서 채널**, 메시지를 받아가는 **컨슈머 채널**이 ****분리되어 있어 채널간 인터셉터 등을 사용 할 수 있다.

### Message Channel Interface

- **MessageChannel**
- **PollableChannel**
- **SubscribeChannel**

### Message Channel Impliment

위의 메시지 인터페이스를 구현한 구연체들이 존재한다.

- **publishSubscribeChannel**: 전달받은 Message를 자신을 구독한 모든 Handler에게 전달한다.

- **QueueChannel:** Point to Point로 해당 채널의 컨슈머가 여럿 있더라도 하나의 컨슈머에 전달한다. 
**큐가 가득차면 send 메소드를 호출 시 sender를 블록킹한다**. 
**큐가 비어있다면 recevie호출이 블록킹 된다.**

- **PriorityChannel:** 큐 채널은 First -In-First-Out을 따르지만 PriorityChannel은 메시지마다 **우선순위**를 부여할 수 있다.
- **RendezvousChannel:** receive를 호출 하기 전까지 s**ender를 블록한다.**

- **DirectChannel:** point-to-pint로 동작하며  PollableChannel을 구현한것이 아닌 S**ubscribeChannel을 구현하였기에 메시지를 직접 Handdlrt에 전달한다.**  
Poller방식이 아니기 때무에 스레드를 스케줄링 하지 않아 오버헤드가 없기 때문에 **Spring Integration의 디폴트 채널이다**.
구독하고 있는 MessageHandler를 호출하는 일을 **메시지 디스페처에 위임한다.**

- **ExcutorChannel:** DirectChannel과 같지만 MessageHandler를 호출하는 디스패치 수행을 **TastExcutor에게 위임**한다는 것이다.

- **FlusMessageChannel: 리액티브 구독자**가 온맨드로 컨슘해갈 수 있도록 전달받은 메시지를 변경하는 채널

---

## MessageEndpoint(Filter)

Filter는 메시지를 컨슘하는 모든 구성요소를 말한다.

### Message Transformer

메시지의 내용 및 구성요소를 변환하고 수정한 메시지를 반환하는 일을 한다. 
Ex) Byte를 String으로 변환한다던지.

### MessageFilter

메시지 필터는 해당 메시지를 다른 채널로 전달할지 말지를 결정한다. 필터에서는 간단한 boolean 테스트 메소드가 있으면 된다. Ex) header 값에 따른 전달 여부 판단.

### MessageRouter

메시지 라우터는 해당 메시지를 여러 채널중 분기처리를 할 때 사용된다. 
Ex) 콜 시작 메시지 → callStartChannel, 콜 종료 메시지 → callEndChannel

### Spliter

입력 받은 메시지를 여러 메시지로 분할하는 역할을 담당하며 일반적으로 “복합”페이로드를 세분화하여 여러 메시지로 나누는데 사용한다.

### Aggregator

Spliter와 반대로 여러 메시지를 하나의 메시지로 통합할 때 사용된다.

### ServiceActivator

**서비스 인스턴스를 메시지 시스템에 연결하기 위한 범용 엔드포인트이다.** 입력 채널을 반드시 구성해야하며 반환할 수 있는 경우 출력 체널도 지정할 수 있다.

### ChannelAdeptor

채널 어뎁터는 메시지 **채널을 다른 시스템이나** 전송 구성요소에 연결해주는 엔드포인트다. 어뎁터는 인바운드와 아웃바운드로 구성된다.

---

간단한 예제

https://github.com/Kmmanki/spring_integration_study_1

해당 예제는 pythonSender - Spring Integration - pythonReceiver 로 구성되어 있으며 

python과 Integration은 TCP 소켓으로 연결한다.

python sender에서 1 이라는 입력을 주면 spring integration에서 1 > 2 메시지를 만들어 pythonReceiver 전달하고 pythonReceiver 는 전달 받은 메시지에 > 3을 붙여 1 > 2 > 3을 만들어 integration으로 reply한다. integration은 전달받은 1 > 2 > 3에 > 4를 붙여 1 > 2 > 3 > 4를 다시 reply하여 sender로 전송한다.

<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbDyA7L%2FbtsF2R2LgIG%2FagMWCpgKYbGYvPdfZhRi2K%2Fimg.jpg"/>

## 동작하는 동영상 URL
https://patrasche25.tistory.com/110

