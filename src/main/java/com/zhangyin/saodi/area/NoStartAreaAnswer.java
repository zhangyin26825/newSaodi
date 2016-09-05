package com.zhangyin.saodi.area;

import java.util.Stack;

/**
 * 针对一个区域的无起点解决答案
 * 所谓的无起点，是指，从某个虚拟节点进入，行进某些 Step,从某些节点离开，
 * 依次遍历完所有的节点，
 * 无起点是指，从某个虚拟节点开始进入
 * 有起点是指，从区域内某个真实节点，开始，行进某些Step,从某些虚拟节点离开，进入
 * 依次 遍历所有的节点
 * 
 * @author Administrator
 */
public class NoStartAreaAnswer {
	
	Area area;
	
	Stack<Step> result;

	public NoStartAreaAnswer(Area area, Stack<Step> result) {
		super();
		this.area = area;
		this.result = result;
	}
	
	

}
