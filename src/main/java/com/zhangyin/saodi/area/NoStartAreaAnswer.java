package com.zhangyin.saodi.area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.zhangyin.saodi.base.VirtualNode;

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
	
	private Area area;
	
	private Stack<Step> result;
	
	private List<Step> list;
	
	private Set<VirtualNode> from;
	
	private Set<VirtualNode> to;
	
	private boolean del=false;
	
	public NoStartAreaAnswer(Area area, Stack<Step> resul) {
		super();
		this.area = area;
		this.result = new Stack<Step>();
		this.result.addAll(resul);
		list=new ArrayList<Step>(result.size());
		from=new HashSet<>();
		to=new HashSet<>();
		int i=list.size();
		while(!result.empty()){
			list.add(result.pop());
		}
		for (Iterator iterator = resul.iterator(); iterator.hasNext();) {
			Step step = (Step) iterator.next();
			if(!step.start.isReal()){
				from.add((VirtualNode)step.start);
			}
			if(!step.end.isReal()){
				to.add((VirtualNode)step.end);
			}		
		}
		
	}
	public Area getArea() {
		return area;
	}



	public void setArea(Area area) {
		this.area = area;
	}



	public Stack<Step> getResult() {
		return result;
	}



	public void setResult(Stack<Step> result) {
		this.result = result;
	}



	public List<Step> getList() {
		return list;
	}



	public void setList(List<Step> list) {
		this.list = list;
	}



	public Set<VirtualNode> getFrom() {
		return from;
	}



	public void setFrom(Set<VirtualNode> from) {
		this.from = from;
	}



	public Set<VirtualNode> getTo() {
		return to;
	}



	public void setTo(Set<VirtualNode> to) {
		this.to = to;
	}
	public boolean isDel() {
		return del;
	}
	public void setDel(boolean del) {
		this.del = del;
	}
	
	
	
	
	
	
	
}
