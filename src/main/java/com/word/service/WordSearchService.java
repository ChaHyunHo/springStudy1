package com.word.service;

import javax.inject.Inject;
import javax.inject.Named;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordSearchService {
	
	@Inject
	@Named("wordDao1") // 동일한 빈객체의 구분을 할때 사용한다.
	private WordDao wordDao;
	
	public WordSearchService() {
		
	}

	public WordSearchService(WordDao wordDao) {
		this.wordDao = wordDao;
	}
	
	public WordSet searchWord(String wordKey) {
		if(verify(wordKey)) {
			return wordDao.select(wordKey);
		} else {
			System.out.println("WordKey information is available.");
		}
		
		return null;
	}
	
	public boolean verify(String wordKey){
		WordSet wordSet = wordDao.select(wordKey);
		return wordSet != null ? true : false;
	}
	
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}
}
