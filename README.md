# springstudy1

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
   
    