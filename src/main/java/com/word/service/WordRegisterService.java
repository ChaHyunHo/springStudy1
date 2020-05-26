package com.word.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.word.WordSet;
import com.word.dao.WordDao;

public class WordRegisterService {
	
	@Resource(name = "wordDao")
	private WordDao wordDao;
	
	public WordRegisterService() {
		
	}
	
	@Autowired
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
	
	@Autowired
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
	}

}
