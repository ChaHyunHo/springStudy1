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

<img src="/ChaHyunHo/springStudy1/blob/master/images/model1.PNG" width="450px" height="300px" title="model1" alt="model1"></img><br/>



    