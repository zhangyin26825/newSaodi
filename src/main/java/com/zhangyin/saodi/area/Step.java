package com.zhangyin.saodi.area;

import java.util.Stack;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;
/***
 * 搜索行进的一步
 *  start  开始的节点
 *  end    结束的节点
 *  
 *  d  从start开始的方向
 *
 */
public class Step {
	/**
	 *  不是第一步，mark的时候，只要把  s 中的所有元素mark就行了
	 *  是第一步的话，mark的时候，需要把start也mark掉
	 */
	boolean isfirst=false;
	
	AbstractNode  start;
	
	AbstractNode  end;
	
	Direction  d;
	/**
	 * q不包含 start,包含end
	 */
	Stack<AbstractNode> s;
	
	
	
	
	public Step(boolean isfirst, AbstractNode start, AbstractNode end, Direction d, Stack<AbstractNode> s) {
		super();
		this.isfirst = isfirst;
		this.start = start;
		this.end = end;
		this.d = d;
		this.s = s;
	}




	public void mark(boolean b){
		s.forEach(a->a.setMarked(b));
		if(isfirst){
			start.setMarked(b);
		}
	}
}
