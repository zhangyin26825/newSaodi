package com.zhangyin.saodi.area;

import java.util.Stack;

public class EndAreaAnswer {
	Area area;
	
	Stack<Step> result;

	public EndAreaAnswer(Area area, Stack<Step> resul) {
		super();
		this.area = area;
		this.result = new Stack<Step>();
		this.result.addAll(resul);
	}

}
