package com.brms.book.service;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.brms.book.Book;
import com.brms.book.dao.BookDao;

public class BookRegisterService implements InitializingBean, DisposableBean {
	// InitializingBean, DisposableBean 생성 및 소멸 시점에서의 특정작업을 할 수 있음
	
	@Autowired
	private BookDao bookDao;
	
	public BookRegisterService() { }
	
	public void register(Book book) {
		bookDao.insert(book);
	}
	
	public void initMethod() {
		System.out.println("BookRegisterService 빈(Bean)객체 생성 단계");
	}
	
	public void destroyMethod() {
		System.out.println("BookRegisterService 빈(Bean)객체 소멸 단계");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("Bean 객체 생성");

	}

	@Override
	public void destroy() throws Exception {
		System.out.println("Bean 객체 소멸");
		
	}
}