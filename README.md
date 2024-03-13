+ # Spring-batch
  + ## 핵심 패턴
    + Read - 데이터베이스, 파일, 큐에서 다량의 데이터를 조회한다.
    + Process - 특정 방법으로 데이터를 가공한다.
    + Write - 데이터를 수정된 양식으로 다시 저장한다.
  + ## 배치 시나리오
    + 배치 프로세스를 주기적으로 커밋
    + 동시 다발적인 Job의 배치 처리, 대용량 병렬 처리
    + 실패 후 수동 또는 스케줄링에 의한 재시작
    + 의존관계가 있는 step 여러 개를 순차적으로 처리
    + 조건적 Flow 구성을 통한 체계적이고 유연한 배치 모델 구성
    + 반복, 재시도, Skip 처리
  + ## 배치 구성도
    + Applicaton : 
      + 스프링 배치 프레임워크를 통해 개발자가 만든 모든 배치 Job과 커스텀 코드를 포함
      + 개발자는 업무로직의 구현에만 집중하고 공통적인 기반기술은 프레임웍이 담당하게 한다.
    + Batch Core : 
      + Job을 실행, 모니터링, 관리하는 API로 구성되어 있다.
      + JobLauncher, Job, Step, Flow 등이 속한다.
    + Batch Infrastructure : 
      + Application, Core 모두 공통 Infrastructure 위에서 빌드한다.
      + Job 실행의 흐름과 처리를 위한 틀을 제공함
      + Reader, Processor Writer, Skip Retry 등이 속한다.
  + ## 배치 설명
    + @EnableBatchProcessing
        + 스프링 배치가 작동하기 이해 선언해야 하는 필수 어노테이션
        + 총 4개의 설정 클래스를 실행시키며 스프링 배치의 모든 초기화 및 실행구성이 이루어짐
        + 스프링 부트 배치의 자동 설정 클래스가 실행됨으로
        + 빈으로 등록된 모든 Job을 검색해서 초기화와 동시에 Job을 수행하도록 구성
        + 순서 :
            1. @EnableBatchProcessing
            2. SimpleBatchConfiguration
            3. BatchConfigurerCofiguration
            4. BatchAutoConfiguration
      + BatchAutoConfiguration
          + 스프링 배치가 초기화 될 때 자동으로 실행되는 설정 클래스
          + Job을 수행하는 JobLauncherApplicationRunner 빈을 생성 (스프링 부트가 자동으로 Job을 실행시키게 해줌)
      + SimpleBatchConfiguration
          + JobBuilderFactory와 SteopBuilderFactory 생성
          + 스프링 배치의 주요 구성 요소 생성 - 프록시 객체로 생성됨
      + BatchConfigurerCofiguration
          + <span style="color:red">사용자 정의 BatchCofigurer 인터페이스를 구현하여 사용할 수 있음</span>
          + BasicBatchConfigurer
              + SimpleBatchConfiguration에서 생성한 프록시 객체의 실제 대상 객체를 생성하는 설정 클래스
              + 빈으로 의존성 주입 받아서 주요 객체들을 참조해서 사용할 수 있다.
              +
          + JpaBatchConfigurer
              + JPA관련 객체를 생성하는 설정 클래스