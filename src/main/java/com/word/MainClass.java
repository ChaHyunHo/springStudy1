package com.word;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.word.service.WordRegisterService;
import com.word.service.WordSearchService;

/**
 * word 패키지는 자동주입에 대한 학습내용이다.
 * @Autowired , @Resource 어노테이션의 각 특징을 알아보고 어떻게 사용하는지 알 수 있다.
 * @author chamy
 *
 */
public class MainClass {

	public static void main(String[] args) {
		
		String[] keyWords = {"java", "jsp"};
		String[] values = {"자바는 썬 마이크로시스템즈의 제임스 고슬링과 다른 연구원들이 개발한 객체 지향적 프로그래밍 언어이다.",
				"jsp는 HTML 내에 자바 코드를 삽입하여 웹 서버에서 동적으로 웹 페이지를 생성하여 웹 브라우저에 돌려주는 언어이다."};
		
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath*:spring/context-word.xml");
		
		WordRegisterService registerService = 
				ctx.getBean("registerService", WordRegisterService.class);
		
		for( int i = 0; i< values.length; i++) {
			WordSet wordSet = new WordSet(keyWords[i], values[i]);
			registerService.register(wordSet);
		}
		
		WordSearchService searchService = 
					ctx.getBean("searchService", WordSearchService.class);
		
		System.out.println("\n\n--------------------------");
		for(int i = 0; i < keyWords.length; i++) {
			WordSet wordSet = searchService.searchWord(keyWords[i]);
			System.out.println(wordSet.getWordKey() + "\t: ");
			System.out.println(wordSet.getWordValue());
			System.out.println("[출처:위키피디아(https://www.wikipedia.org/)]");
			System.out.println("---------------------------------------");
		}
		System.out.println("\n\n");
		
		ctx.close();
	}

}
