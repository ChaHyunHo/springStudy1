package com.scope;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.scope.ex.DependencyBean;
import com.scope.ex.InjectionBean;

/**
 * 현재 scope 패키지에 있는 코드는 프로토타입 패턴을 이용한 의존성주입을 공부하기 위한 코드이다.
 * 설정 파일은 contenx-common.xml을 사용함.   
 * @author chamy
 *
 */
public class MainClass {  

	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath*:spring/context-common.xml");
		
		InjectionBean injectionBean = 
					ctx.getBean("injectionBean", InjectionBean.class);
		
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
	}

}
