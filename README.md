# 목차 

### [이클립스와 github](#이클립스에서 터미널(git bash)과 github저장소 환경 셋팅방법)

## 이클립스에서 터미널(git bash)과 github저장소 환경 셋팅방법

#### 1. git bash를 이용한 'git init'  해당 폴더에 git init 명령어를 입력하면 폴더에 .git 이라는 익명폴더가 생성된다.(저장소 셋팅완료)

#### 2. README.md 를 생성하고 싶다면 echo "# example11" >> README.md  입력

#### 3. 변경 사항이나 모든 새로운 파일 생성시 'git add' 로 등록을 해주어야 한다. 등록하고 싶은것만 따로 한다면 해당 파일 이름을 입력 전체 등록한다면 git add . <- .을 입력

#### 4. index 등록이 완료 되었다면 커밋을 하면된다.  git commit -m "first commit"  -m은 커밋 메세지를 입력

#### 5. github 레파지토리 생성 후 로컬에 있는 저장소와 리모트 저장소를 연결시켜준다.   
ex) git remote add origin https://github.com/ChaHyunHo/example11.git ( origin 이후 해당 레파지토리 저장소 주소를 입력)   

#### 6. 커밋 과정이 끝난 후 push를 하게되면 리모트 저장소에 코드가 반영된다.  'git push'



# DI(Dependency injection) ?

### DI는 스프링만의 기능은 아님 

### OOP 프로그래밍에서 프로그래밍을 하는 하나의 방법론중 하나임

### 객체를 만들어 웹에 따로 주입하는 형태를 의존 주입이라고함.

ex) DI의 기본 개념 (자바 코드로 예)
<pre>
<code>
	public class ElectronicRadioToy {

		private Battery battery;
	
		public ElectronicRadioToy(Battery battery) {
			this.battery = battery;	// 처음 배터리를 생성자로 초기화 시켜준다.
		}
		
		public void setBattery(Battery battery) {
			this.battery = battery;		// 배터리가 다 떨어지면 외부에서 사용자가 배터리를 교체한다.
		}
	}
</code>
</pre>
빈(Bean)객체를 필요로 하는 로직은 스프링 컨테이너를 통해 'getBean'으로 가지고 온다. 
가지고 온 객체중에는 다른 객체에 의존되어있는 객체도 있다. 

# 스프링에서 의존객체를 주입하는 방법

## 1. 생성자를 이용한 객체주입 
ex)
생성자 주입(자바코드)
```
public StudentRegisterService(StudentDao studentDao) { 
	this.studentDao = studentDao;
}
```
(xml 코드)
```
<bean id="studentDao" class="com.camel.dao.studentDao"></bean>( studentDao 객체를 생성)
<bean id="registerService" class="com.camel.dao.studentService">
	<contructor-arg ref="studentDao"><contructor-arg> 
	// 생성자에서 학생객체를 주입할때는 contructor-arg(생성자 파라미터)를 studenDao를 입력해준다.
</bean>
```
## 2. setter를 이용한 의존 객체 주입
```
public void setJdbcUrl(String jdbcUrl) {
	this.jdbcUrl = jdbcUrl;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public void setUserPw(String userPw) {
	this.userPw = userPw;
}

XML
<bean id="dataBaseConnectionInfoDev" class="ems.member.dataBaseConnectionInfo">
	<property name="jdbcUrl" value="jdbc:oracle:thin:@localhost:1521:xe" /> // property의 이름은 해당하는 setter의 set을 뺀 소문자시작 카멜케이스형태로 추가한다.
	<property name="userId" value="scott" /> // 파라미터로 들어온 값을 value로 셋팅
	<property name="userPw" value="tiger" />
</bean>
```
## 3. List타입 의존 객체를 주입
```
public void setDevelopers(List<String> developers) {
	this.developers = developers;
}
```
XML
```
<property name="developers"> // set 생략 및 소문자시작 카멜케이스 형태
	<list> // 각 프로퍼티의 대한 리스트이기 때문에 리스트 형태로 감싼다.
		<value>cheney.</value>
		<value>Eloy.</value>
		<value>jasper.</value>
		<value>Dillon.</value>
	</list>
</property>		
```
## 4. Map타입 객체 주입 
```
public void setAdministrators(Map<String, String> administrators) {
	this.administrators = administrators;
}
```
XML
```
<property name="administrators">
	<map>
		<entry>
			<key>
				<value>Cheney</value>
			</key>
				<value>cheney@springPjt.org</value>
		</entry>
		<entry>
			<key>
				<value>Jasper</value>
			</key>	
				<value>jasper@springPjt.org</value>
		</entry>
	</map>
</property>	
```

# 스프링 설정 파일분리

#### appliction-context의 설정이 복잡하고 긴경우 컨텍스트 설정을 분리하여 설정할 수 있다.
#### ex) appliction-context.xml의 세부 내용은 작성하지 않겠지만 내용이 길다는 가정하에 설명한다.
#### 내용을 구분할때는 서로 연관된 내용으로 구분하여 작성한다. 
#### context-common.xml , context-datasorce.xml, context-mapper.xml 등등 

#### 컨테이너의 빈을 사용할시 구분되어있는 설정들을 어떻게 불러서 사용해야될까?
```
String[] appCtxs = 
	{"classpath:context-common.xml", "classpath:context-datasorce.xml", "classpath:context-mapper.xml"};
	
	GenericXmlApplicationContext ctx = 
			new GenericXmlApplicationContext(appCtxs);
```
위 코드처럼 배열 형태로 담아서 사용하면 된다. 단 위 설정빈들의 이름은 무시

설정 파일에 임포트하여 하나로 묶어 사용할 수도 있다.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd">
	<!-- Root Context: defines shared resources visible to all other web components -->
	
	<import resource="classpath:spring/context-datasorce.xml"/>
	<import resource="classpath:spring/context-mapper.xml"/>
</beans>
```
따라서 배열에 담지않고 하나의 설정파일에 담아 사용할 수 있다. 

### context-common.xml

```
	GenericXmlApplicationContext ctx = 
			new GenericXmlApplicationContext("classpath:context-common.xml");
```

#  빈(Bean)의 범위
	스프링 설정파일-> GenericXmlApplicationContext -> Spring Container -> 여러개의 빈(Bean)객체 -> getBean을 통한 참조로 빈(Bean)사용
	getBean을 이용하여 스프링컨테이너의 빈을 가져오게되면 어디서 사용하던 같은 객체를 가져온다.
	자바 코드를 이용하여 객체를 가져올때 new 키워드를 통해 객체를 생성한다.
	
	하지만 스프링은 GenericXmlApplicationContext클래스가 이미 스프링 컨테이너를 만들면서 등록된 빈객체를 생성했다.
	여러곳에서 "A"라는 객체를 호출 해도 "A"객체 하나만 사용할 뿐이다. 

#### 1. 싱글톤(singleton)
	스프링 컨테이너에서 생성된 빈(Bean)객체의 경우 동일한 타입에 대해서는 기본적으로 한 개만 생성이 되며, getBean() 메소드로 호출될 때
	동일한 객체가 반환 된다.
    
#### 2. 프로토타입(Prototype)
	싱글톤 범위와 반대의 개념도 있는데 이를 프로토타입(Prototype)범위라고 한다.
	프로토타입의 경우 개발자는 별도로 설정을 해줘야 하는데, 스프링 설정 파일에서 
	빈(Bean)객체를 정의할 때 scope속성을 명시해 주면 된다.
	
프로토타입 ex)
프로토타입은 싱글톤과는 달리 객체를 각각 만들어서 참조하는 형태
```
<bean id="injectionBean" class="scope.ex.InjectionBean" />

<bean id="dependencyBean" class="sope.ex.DependencyBean" scope="prototype"> // scpoe="prototype"만 작성해주면 프로토타입이됨
	<constructor-arg ref="injectionBean" />
	<property name="injectionBean" ref="injectionBean" />
</bean>
```

프로토타입으로 의존받고있는 클래스 형태
```
	// 클래스 일부 
	DependencyBean dependencyBean1 = 
					ctx.getBean("dependencyBean", DependencyBean.class);
		
		DependencyBean dependencyBean2 = 
				ctx.getBean("dependencyBean", DependencyBean.class);
		
		// dependenctBean =>  scope="prototype"으로 주게되면 싱글톤 패턴과 반대로 객체를 각각 만들어 준다. 
		if(dependencyBean1.equals(dependencyBean2)) {
			System.out.println("dependencyBean1 == dependencyBean2");
		} else {
			// 프로토타입 패턴이라면 각각 다른 객체로 표시된다. 
			System.out.println("dependencyBean1 != dependencyBean2");
		}
		
		ctx.close();
		
		// 결과는 dependencyBean1 != dependencyBean2
```
 
 # 의존객체 자동 주입
 
 	의존 객체 자동 주입이란?
 	스프링 설정 파일에서 의존 객체를 주입할 때 <constructor-arg> 또는 <property> 태그로 의존 대상 객체를 명시하지 않아도
 	스프링 컨테이너가 자동으로 필요한 의존 대상 객체를 찾아서 의존 대상 객체가 필요한 객체에 주입해 주는 기능이다.
 	구현 방법은 @Autowired와 @Resource 어노테이션을 이용해서 쉽게 구현할 수 있다.
 
 ```
 	<!-- 어노테이션 사용시 선언  -->
	<context:annotation-config />
	
	<bean id="wordDao" class="com.word.dao.WordDao" />
	
	<bean id="registerService" class="com.word.service.WordRegisterService" >
		<!-- <constructor-arg ref="wordDao" /> --> <!-- 생성자를 통한 의존주입 어노테이션 선언시 생략   -->
	</bean>
	
	<bean id="searchService" class="com.word.service.WordSearchService" >
		<!-- <constructor-arg ref="wordDao" /> -->
	</bean>
 ```
 	
 #### @Autowired 어노테이션
 	주입하려고 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
 	
 	생성자에 @Autowired을 선언하여 사용할 수 있지만 프로퍼티나 메소드에 사용하기 위해서는
 	디폴트 생성자를 선언하여 사용해야한다.
 	
```
	public class WordRegisterService {
	
	@Autowired    // 프로퍼티에 주입을 한경우
	private WordDao wordDao;
	
	public WordRegisterService() { // 프로퍼티나 메소드에 @Autowired 사용시 디폴트 생성자를 선언하여 사용한다.
	}							   // 프로퍼티나 메소드에 디폴트생성자가 필요한 이유는 프로퍼티나 메소드는 선언하기 이전에
								   // 객체가 선언이 되어야 하기 때문이다.
	
	@Autowired   // 생성자를 통해 주입을 한경우
	public WordRegisterService(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
	public void register(WordSet wordSet) {
		String wordKey = wordSet.getWordKey();
		if(verify(wordKey)) {
			wordDao.insert(wordSet);
		} else {
			System.out.println("The word has already registered.");
		}
	}
	
	public boolean verify(String wordKey){
		WordSet wordSet = wordDao.select(wordKey);
		return wordSet == null ? true : false;
	}
	
	@Autowired   // setter을 통해 주입한경우
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}

}
```

 #### @Resource 어노테이션
 	주입하려고 하는 객체의 이름이 일치하는 객체를 자동으로 주입한다. 
 	(@Autowired와 기능은 동일하지만 이름으로 찾는게 가장 큰특징)
 	
 	@Resource는 생성자에 사용할 수 없다.
 	프로퍼티 또는 메소드에 사용 가능하다.
 	
 ```
	@Resource    // 객체의 타입을 보지않고 이름으로 찾는다.
	private WordDao wordDao;
	
	
	<bean id="=wordDao" class"com.word.dao.WordDao" /> // id <> 프로퍼티 이름
	
	
	@Resource(name = "wordDao") // name을 이용해 명시적으로 사용할 수도 있다.
	private WordDao wordDao;
	
	// javax에 어노테이션 패키지가 없다면(Resource어노테이션이 없음) 
	<dependency>
		<groupId>javax.annotation</groupId>
		<artifactId>javax.annotation-api</artifactId>
		<version>1.3.1</version>
	</dependency>
	// 메이븐 설정을 추가해주면된다.
 ```

# 의존객체 선택 
	다수의 빈(Bean)객체 중 의존 객체의 대상이 되는 객체를 선택하는 방법

#### 의존객체 선택	

	스프링 컨테이너에서 동인한 객체가 2개 이상인 경우 스프링 컨테이너는 
	자동 주입 대상 객체를 판단하지 못해서 Exception을 발생시킨다.
	
	// Exception의 내용은 다음과 같다.
	Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException: 
	Error creating bean with name 'registerService' defined in URL [file:/C:/spring-tool-suite-4-4.5.1.RELEASE-e4.14.0-win32.win32.x86_64.self-extracting/sts-4.5.1.RELEASE/workspace/camelstudy/target/classes/spring/context-word.xml]: Unsatisfied dependency expressed through constructor parameter 0; 
	nested exception is org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
	No qualifying bean of type 'com.word.dao.WordDao' available: expected single matching bean but found 3: wordDao1,wordDao2,wordDao3
	
ex)
```
	<bean id="wordDao1" class="com.word.dao.WordDao" >
		<qualifier value="usedDao" /> // @Qualifier과 동일한 value값을 정해주면 에러 x
	</bean>
	<bean id="wordDao2" class="com.word.dao.WordDao" />
	<bean id="wordDao3" class="com.word.dao.WordDao" />
	
	@Autowired
	@Qualifier("usedDao")  //<-- 동일한 타입의 객체가 존재할 경우 
	private WordDao wordDao;

```

#### 의존객체 자동 주입 체크
	
	
```
	<!-- <bean id="wordDao" class="com.word.dao.WordDao" /> -->
	
	@Autowired(required = false)
	private WordDao wordDao;
	// @Autowired는 알맞은 데이터 타입을 찾아서 빈객체를 참조하게 되는데 위 같이 주석처리한경우
	// 빈을 찾을 수 없는 익셉션이 발생하게 된다. 그럴경우 에러를 무시고 하고 싶다면 required = false를
	// 하면되는데 이경우 빈이 없어도 무시하게된다. 그치만 그리 좋지 않은 방법이므로 지양할 필요성이 있다.
```

#### @Inject 어노테이션
	@Autowired와 거의 비슷하게 @Inject 어노테이션을 이용해서 의존 객체를 자동으로 주입을 할 수 있다.
	@Autowired와 차이점 이라면 @Autowired의 경우 required속성을 이용해서 의존 대상 객체가 없어도 익셉션을 피할 수 있지만,
	@Inject의 경우 required속성을 지원하지 않는다.

```	
    @Inject
	@Named("wordDao1")  //<-- 동일한 타입의 빈 객체가 존재할 경우 
	private WordDao wordDao; // @Autowired와는 달리 'named'라는 어노테이션을 쓰고 
	
	<bean id="wordDao1" class="com.word.dao.WordDao" >
	<!-- <qualifier value="usedDao" /> --> // @Qualifier는 사용하지 않는다.
	</bean>
	<bean id="wordDao2" class="com.word.dao.WordDao" />
	<bean id="wordDao3" class="com.word.dao.WordDao" />
```	
	
	
# 생명주기(Life Cycle)

#### 스프링 컨테이너 생명주기

	1. GenericXmlApplicationContext를 이요한 스프링 컨테이너 초기화(생성)
	
		// 컨테이너가 생성되고 각각의 빈들은 
		// 의존객체에 주입되어 사용된다.(즉 컨테이너의 생성시점과 빈들의 생성시점은 동일하다.)
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath*:spring/context-common.xml");
				
				
	2. getBean()를 이용한 빈(Bean)객체 이용
		
		InjectionBean injectionBean = 
					ctx.getBean("injectionBean", InjectionBean.class);
		
		DependencyBean dependencyBean1 = 
					ctx.getBean("dependencyBean", DependencyBean.class);
		
		DependencyBean dependencyBean2 = 
					ctx.getBean("dependencyBean", DependencyBean.class);
	
	
	3. close()를 이용한 스프링 컨테이너 종료
	
		ctx.close();  // 스프링 컨테이너를 헤제하는 시점이 바로 소멸시점이다.(Bean들도 자동으로 소멸된다.)
		

#### 빈(Bean)객체 생명주기
	
	스프링 컨테이너 초기화 -> 빈(Bean)객체 생성 및 주입
	
	스프링 컨테이너 종료 -> 빈(Bean)객체 소멸
	
	* 빈(Bean)객체의 생명주는 스프링 컨테이너의 생명주기와 같이 한다.
	
	<<interface>>
	InitializingBean , DisposableBean
	
	빈(Bean)이 생성 및 소멸되는 시점에 특정한 작업을 하고싶을경우  InitializingBean , DisposableBean 인터페이를 이용한다.
	주로 생성 및 소멸되는 시점에서 사용하게 되는 기능은 예로 DB인증이나 다른 PC에서의 인증절차 등으로 사용할 수 있다.

ex)
```
public class BookRegisterService implements InitializingBean, DisposableBean {
// InitializingBean, DisposableBean 생성 및 소멸 시점에서의 특정작업을 할 수 있음

	@Autowired
	private BookDao bookDao;
	
	public BookRegisterService() { }
	
	@Override
	public void afterPropertiesSet() throws Exception { // InitializingBean의 추상 메소드
		System.out.println("Bean 객체 생성");
	
	}
	
	@Override
	public void destroy() throws Exception { // DisposableBean의 추상 메소드
		System.out.println("Bean 객체 소멸");
		
	}
}

```

#### init-method, destroy-method 속성

위 경우 인터페이스를 이용하여 생성 및 소멸시점에 필요한 작업을 했지만 init-method 나 destroy-method
경우 XML 설정 파일에 해당빈에서 선언하여 메소드 이름으로도 사용할 수 있다.

init-method 속성을 이용하는 것과 인터페이스를 이용하는 것은 개발자의 취양에 따라 다르므로
어떤것이 더 좋고 나쁜것은 없다. 자신이 맞는 쪽으로 사용하면 된다.

ex) XML
```
<bean id="bookRegisterService" class="com.brms.book.service.BookRegisterService"
	init-method="initMethod" destroy-method="destroyMethod"  ></bean>
	// 해당 빈에 속성의 이름이 메소드의 이름과 동일해야한다.

```
```
public class BookRegisterService {
	
	@Autowired
	private BookDao bookDao;
	
	public BookRegisterService() { }
	
	public void register(Book book) {
		bookDao.insert(book);
	}
	
	public void initMethod() { // 빈속성 이름과 동일 initMethod
		System.out.println("BookRegisterService 빈(Bean)객체 생성 단계");
	}
	
	public void destroyMethod() {
		System.out.println("BookRegisterService 빈(Bean)객체 소멸 단계");
	}
}
```

springStudy2에 이후 수업

# 웹 프로그래밍 설계 모델


#### 웹 프로그래밍을 구축하기 위한 설계 모델

<img src="./images/model1.PNG" width="650px" height="350px" title="model1" alt="model1"></img><br/>
	WAS에서 사용자로 부터 받은 요청을 하나에 JSP(JAVA + <태그>)페이지를 통해 전부 처리해 버리는 형태이다.
	하나의 페이지에 모든것을 할 수 있어서 개발속도가 빠르다. 하지만 코드가 복잡하고 유지보수 차원에서
	좋지 않다.

<img src="./images/model2.PNG" width="650px" height="350px" title="model2" alt="model2"></img><br/>
	모델2 방식은 모델1의 단점을 보완하기 위해 나온 모델이다. WAS에서 사용자로 부터 받은 요청을 
	Controller가 받게된다. Controller는 사용자의 요청에따라 어떤 서비스로 다음 작업을 할지에 대해
	정해주는 역할을 한다. Service(비즈니스 로직)또한 기능별로 나뉘어 처리를 하게된다. DAO라는 모듈을 또 따로 만들었는데
	이것은 model이라는 객체를 통해서 데이터베이스와 통신을 하는 역할을 한다. 데이터베이스에서 부터 가지고온
	데이터를 역순으로 다시 가지고와서 View라는 객체가 JSP를 만들어 클라이언트에게 전해주는 방식이다.
	모델1과는 다르게 WAS에서 각각의 모듈로 나뉘어 체계적으로 작업을 하게된다. 이 작업을 보통 MVC패턴이라고 하고
	이러한 작업을 바탕으로 유지보수를 쉽게 할 수 있는 장점이있다.
	
#### 스프링에서 보는 MVC 설계 구조

<img src="./images/mvc.PNG" width="650px" height="350px" title="mvc" alt="mvc"></img><br/>
* DispatcherServlet
  * 모든 연결을 담당한다
  * 클라이언트의 요청을 받으면 DispatcherServlet 객체가 받는다.
  * HandlerMapping에게 요청을 던지고 다시 받는다.
  * HandlerAdapter에게 요청을 던지고 다시 받는다.
  * HandlerAdapter를 통해 받은 Model 과 View를 ViewResolver 전달

* HandlerMapping
  * 여러개의 Controller중에 요청에 맞는 적합한 것을 매핑해준다. 
  * Controller요청을 선택해주고 다시 DispatcherServlet으로 온다.
 
* HandlerAdapter
  * HandlerAdapter는 HandlerMapping이 정해준 Controller 요청에 적합한 메소드를 매핑해준다. 
  * 해당 메소드의 결과 값을 Model과 View 데이터로 가져온다.
 
* ViewResolver
  *  View 정보를 통해 알맞은 JSP 페이지로 응답을 생성해 클라이언트로 보내준다.
  
* 순서
  1. 요청받은 DispatcherServlet 객체는 해당 요청을 HandlerMapping에게 넘기고 HandlerMapping은 알맞은 요청 컨트롤러를 선택해준다.
  2. HandlerMapping과정이 끝나고 DispatcherServlet은 HandlerAdapter로 HandlerMapping이 정해준 Controller 요청에 적합한 메소드를 매핑해준 후 해당 메소드의 결과 값을 Model이라는 데이터로 가져온다. 
  3. HandlerAdapter를 통해 컨트롤러 요청 및 메소드 결과 Model과 View정보를 가지고 다시 DispatcherServlet으로 거쳐 ViewResolver에게 가는데 해당 View 정보를 통해 알맞은 JSP 페이지로 응답을 보내준다.  
  
 정리 요약 이미지
 <img src="./images/mvc2.PNG" width="950px" height="650px" title="mvc2" alt="mvc2"></img><br/>  
 
 
 
#### DispatcherServlet 설정
	(사전 지식) web.xml 이란?
	web.xml파일은 웹 어플리케이션의 배포 설명자로, 각 어플리케이션의 환경을 설정하는 역할을 한다.
	서버가 처음 로딩될 때 읽어들이고, 해당 환경설정에 대해 tomcat에 적용하여 서버를 시작한다.
	dispatcherServlet 설정, db설정과 같은 서브릿 설정에 대한 내용, listener, filter 설정 및 welcome file list, error page 처리,
	mome type매핑, session 의 유효시간 설정, servlet context의 초기 파라미터 설정 등이 있다.
	web.xml파일은 맨 처음 <web-app> 태그로 시작되고, xmlns 네임스페이스로 xml schema for java EE Deployment Descriptors 파일이 존재하는 위치를 선언해주고 있다.

```
web.xml에 서블릿을 매핑

<servlet>
	<servlet-name>appServlet</servlet-name> <!-- 서블릿 별칭 -->
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class> <!-- 서블릿명(패키지 이름을 포함한 전체서블릿명) -->
	<init-param>
		<param-name>contextConfigLocation</param-name> 
		<param-value>/WEB-INF/springmvc/servlet-context.xml</param-value>  <!-- 스프링 설정파일(스프링 컨테이너 생성) -->
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
	
<servlet-mapping>
	<servlet-name>appServlet</servlet-name> <!-- 서블릿 별칭 -->
	<url-pattern>/</url-pattern> <!-- 맵핑명  -->
	<!-- 클라이언트로 부터 받은 모든 요청은 '/'은 루트로 시작됨을 의미 -->
</servlet-mapping>

```

1. 초기화 파리미터에서 지정한 파일(servlet-context.xml)을 이용해서 스프링 컨테이너를 생성(일반적인 방법)
DispatcherServlet 스프링 설정파일(초기화 파라미터) ---> servlet-context.xml 이용한 스프링 컨테이너(HandlerMapping, HandlerAdapter, ViewResolver)

2. 초기화 파라미터에서 스프링 설정 파일을 지정하지 않은 경우 서블릿별칭을 이용해서 스프링 컨테이너 생성
스프링 설정파일 x


#### Controller 객체
	servlet-context.xml에 <annotation-driven />으로 어노테이션을 사용하겠다고 선언을 하고 해당 클래스에 @Controller 어노테이션을 추가해준다.
	
```
@RequestMapping("/success") // 사용자로 부터 들어오는 맵핑 정보를 작성한다.
public String success(Model model) { 
 	return "success";
}

model.setAttribute("tempData","model has data!!")

* 개발자는 Model 객체에 데이터를 담아서 DispatcherServlet에 전달할 수 있다.
* DispatcherServlet에 전달된 Model데이터는 View에서 가공되어 클라이언트한테 응답처리 한다.
```


#### view 객체


```
@RequestMapping("/success") 
public String success(Model model) { 
 	return "success";
}

// ViewResolver를 통해 해당 JSP페이지로 이동시킬때 사용하는 설정
<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<beans:property name="prefix" value="/WEB-INF/views/" />
	<beans:property name="suffix" value=".jsp" />
	<beans:property name="order" value="2" />
</beans:bean>

JSP파일명 : /WEB-INF/views/success.jsp
```

스프링 MVC , Service, Dao 객체 구현, 컨트롤러 객체구현은 생략


# 세션(Session), 쿠키(Cookie)

	Connectionless Protocol
	웹 서비스는 HTTP프로토콜을 기반으로 하는데, HTTP프로토콜은 클라이언트와 서버의 관계를 유지 하지 않는 특징이 있다.
	관계를 유지 하지 않는 이유는 수많은 클라이언트의 요청 정보를 전부 유지하게 되면 부하가 걸리기 때문이다. 따라서 서버의
	효율적인 운영을 위한것이다.
	
	클라이언트 ->요청(Request): 서버 연결 -> 서버
	서버 -> 응답(Response): 서버 연결 해제 ->  클라이언트
	
	Connectionless Protocol
	서버의 부하를 줄일 수 있는 장점은 있으나, 클라이언트의 요청 시마다 서버와 매번 새로운 연결이 생성되기 때문에 일반적인 로그인 상태 유지,
	장바구니 등의 기능을 구현하기 어렵다. 이러한 Connectionless Protocol의 불편함을 해결하기 위해서 세션과 쿠키를 이용한다.
	
	세션과 쿠키는 클라이언트와 서버의 연결 상태를 유지해주는 방법으로, 세션은 서버에서 연결 정보를 관리하는 반면 쿠키는 클라이언트에서 연결 정보를
	관리하는데 차이가 있다.
	
#### HttpSession을 이용 ex)
```
@RequestMapping(value = "/login", method = RequestMethod.POST)
public String memLogin(Member member, HttpSession session) {
	
	Member mem = service.memberSearch(member);
	
	session.setAttribute("member", mem); // 세션에 추가하고 싶은 정보를 입력해준다.
	
	return "/member/loginOk";
}
```
#### HttpServletRequest를 이용한 세션 사용 ex)
```
@RequestMapping(value = "/login", method = RequestMethod.POST)
public String memLogin(Member member, HttpServletRequest request) {
	
	Member mem = service.memberSearch(member);
	
	HttpSession session = request.getSession();
	session.setAttribute("member", mem);
	
	/*
	session 메소드
	
	session.invalidate(); // 세션 객체의 모든 정보를 삭제  (로그아웃 ..)
	
	session.setMaxInactiveInterval(); // 세션 객체의 유지시간을 설정
	
	session.getMaxInactiveInterval(); // 세션 객체의 유지시간을 반환한다.
	
	session.getId() // 세션 ID를 반환 한다.
	
	session.setAttribute() // 세션 객체에 속성을 저장한다.
	
	session.getAttribute() // 세션 객체에 저장된 속성을 반환한다.
	
	session.removeAttribute() // 세션 객체에 저장된 속성을 제거한다.
	*/
	
	return "/member/loginOk";
}
```
HttpServletRequest, HttpSession의 차이점은 거의없다. 단지 세션객체를 얻는 방법에 차이가 있을뿐이다.

#### 쿠키(Cookie)
```
@RequestMapping("/main")
public String mallMain(Mall mall, HttpServletResponse response) {
	Cookie genderCookie = new Cookie("gender", mall.getGender());
	
	if(mall.isCookieDel()) {
		genderCookie.setMaxAge(0);
		mall.setGender(null);
		} else {
		 genderCookie.setMaxAge(60*60*24*30);
		}
		 response.addCookie(genderCookie);
		 
		 return "/mall/main";
		} 
```

* mallMain() 에서 쿠키를 생성하고, 파라미터로 받은 HttpservletResponse에 쿠키를 담고 있다.

* 쿠키를 생성할 때는 생성자에 두 개의 파라미터를 넣어주는데 첫 번째는 쿠키이름을 넣어 주고 두 번째는 쿠키값을 넣어 준다.
	
```
public String mallIndex(Mall mall, @CookieValue(value="gender", require=false) Cookie genderCookie) {
```
@CookieValue 어노테이션의 value 속성은 쿠키 이름을 나타내는데 , 만약 value 에 명시한 쿠키가 없을 경우 익셉션이 발생한다
익셉션을 막는 방법이 있는데 , 바로 required 속성이다 . Required 속성은 기본값으로 true 를 가지고 있는데 required 가 true 인 경우 value 값에 해당하는 쿠키가 없으면 익셉
션이 발생한다 . 따라서 required 속성값을 false 로 설정해서 value 값에 해당하는 쿠키가 없어도 익셉션이 발생하지 않도록 한다
