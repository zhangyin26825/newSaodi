package com.zhangyin.saodi.area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

/** 
 * 虚拟节点 把整个LevelMap 分成若干个区域
 * 
 * 区域的生成，可以从一个虚拟节点，开始广度优先遍历，到达一个虚拟节点就停止
 * 可能的问题是， 虚拟节点是成对存在的，可能造成一对虚拟节点都在某一个区域内，
 * 这个时候，就要清除这两个虚拟节点，把它们取消，还原到没有增加虚拟节点的那种情况下去
 * @author Administrator
 *
 */
public class Area {
	
	Set<AbstractNode> virtualNodes;
	
	Set<AbstractNode>  realnodes;
	
	Stack<Step> result;
	
	List<NoStartAreaAnswer> solves;
	
	List<EndAreaAnswer> endsolves;
	
	Set<AbstractNode> areaNode;

	public Area(Set<AbstractNode> areaNode) {
		virtualNodes=new HashSet<>();
		realnodes=new HashSet<>();
		solves=new ArrayList<>();
		endsolves=new ArrayList<>();
		result=new Stack<>();
		this.areaNode=areaNode;
		for (Iterator iterator = areaNode.iterator(); iterator.hasNext();) {
			AbstractNode abstractNode = (AbstractNode) iterator.next();
			if(abstractNode.isReal()){
				realnodes.add(abstractNode);
			}else{
				virtualNodes.add(abstractNode);
			}		
		}
		chooseVirtualNodes(true);
	}
	
	public void clear() {
		result.clear();
		solves.clear();
		resetMark();	
	}

	public void resetMark(){	
		for (Iterator iterator = areaNode.iterator(); iterator.hasNext();) {
			AbstractNode virtualNode = (AbstractNode) iterator.next();
			virtualNode.setMarked(false);			
		}
	}
	/**
	 * 选择一个虚拟节点开始搜索
	 */
	public void chooseVirtualNodes(boolean isfirst){
		for (Iterator iterator = virtualNodes.iterator(); iterator.hasNext();) {
			AbstractNode abstractNode = (AbstractNode) iterator.next();
			if(abstractNode.isMarked()){
				continue;
			}
			walkFromNode(abstractNode,isfirst);
		}
	}
	
	private void walkFromNode(AbstractNode node,boolean isfirst) {
		Set<Direction> directions = node.getcanMoveDirection();
		if(directions.isEmpty()){
			return;
		}
		for (Iterator iterator = directions.iterator(); iterator.hasNext();) {
			Direction direction = (Direction) iterator.next();
			//依据当前的方向，生成一步
			Stack<AbstractNode> s=new Stack<>();
			AbstractNode temp = node.get(direction);
			while(temp!=null&&!temp.isMarked()){	
				s.push(temp);
				if(!temp.isReal()){
					break;
				}
				temp = temp.get(direction);	
			}
			//如果当前步没有任何一个节点，说明进入了不能移动的地步，
			if(s.isEmpty()){
				continue;
			}
			//end代表当前 步的结束节点
			AbstractNode end=s.peek();
			Step step=new Step(isfirst,node,end,direction,s);
			//把当前步设置为已经标记过
			step.mark(true);
			//把当前步加入到结果的栈上面去
			result.push(step);
			//根据最后节点的状态进行判断，如果是真实节点，递归调用当前的方法
			//如果是虚拟节点，判断当前是否结束，如果不是的话，继续虚拟节点的搜索
			// 这里的情况是，到达一个虚拟节点之后，可以达到当前区域的任意一个虚拟节点，重新开始进行这个步骤
			if(end.isReal()){
				//行走一步之后，到达一个真实节点
				if(isfinish()){
					endsolves.add(new EndAreaAnswer(this, result));
				}	
				walkFromNode(end, false);
			}else{
				//行走一步之后，达到一个虚拟节点
				//判断是否结束，如果结束的话，
				if(isfinish()){
					solves.add(new NoStartAreaAnswer(this, result));
				}else{
					chooseVirtualNodes(true);
				}		
			}
			//当前步骤完成之后，把step设置为未标记，然后从结果斩出栈
			step.mark(false);
			result.pop();	
		}	
		
		
	}
	
	public boolean isfinish(){
		
		for (Iterator iterator = areaNode.iterator(); iterator.hasNext();) {
			AbstractNode abstractNode = (AbstractNode) iterator.next();
			if(!abstractNode.isFinish()){
				return false;
			}
		}
		return true;
	}
	public Set<AbstractNode> getVirtualNodes() {
		return virtualNodes;
	}
	public void setVirtualNodes(Set<AbstractNode> virtualNodes) {
		this.virtualNodes = virtualNodes;
	}
	public Set<AbstractNode> getRealnodes() {
		return realnodes;
	}
	public void setRealnodes(Set<AbstractNode> realnodes) {
		this.realnodes = realnodes;
	}
	public Stack<Step> getResult() {
		return result;
	}
	public void setResult(Stack<Step> result) {
		this.result = result;
	}
	public List<NoStartAreaAnswer> getSolves() {
		return solves;
	}
	public void setSolves(List<NoStartAreaAnswer> solves) {
		this.solves = solves;
	}
	@Override
	public String toString() {
		return "Area [areaNode=" + areaNode + "]";
	}
	public Set<AbstractNode> getAreaNode() {
		return areaNode;
	}
	public void setAreaNode(Set<AbstractNode> areaNode) {
		this.areaNode = areaNode;
	}
	
	
	
	
	
	
	
}
