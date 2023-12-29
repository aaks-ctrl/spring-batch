package com.aayush.spring.batch.reader;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class FirstItemReader implements ItemReader<Integer> {

	List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
	private int i = 0;

	@Override
	public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// this method will be called by Spring Batch and at a time only one value will
		// be returned by this method
		System.out.println("Inside item reader");
		Integer item = 0;
		if (i < list.size()) {
			item = list.get(i++);
			return item;
		}
		i = 0;
		return null;
	}
}
