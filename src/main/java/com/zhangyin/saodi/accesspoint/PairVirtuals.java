package com.zhangyin.saodi.accesspoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.zhangyin.saodi.base.Direction;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;
/**
 * a  b 代表两个真实节点，要在这两个真实节点之间 插入两个虚拟节点
 * 虚拟节点分别绑定这两个真实节点
 * @author Administrator
 *
 */
public class PairVirtuals {
	
	RealNode  a;
	
	RealNode  b;
	
	Direction AtoB;
	
	boolean required;
	
	VirtualNode  nearA;
	
	VirtualNode  nearB;

	public PairVirtuals(RealNode a, RealNode b, Direction atoB) {
		super();
		this.a = a;
		this.b = b;
		if(atoB==null){
			List<Direction> collect = a.keySet().stream().filter(d->a.get(d)==b).collect(Collectors.toList());
			assert collect.size()==1;
			atoB=collect.get(0);
		}
		AtoB = atoB;
		if(a.size()==b.size()&&a.size()==3){
			required=false;
		}else{
			required=true;
		}
		
		assert a.get(AtoB)==b;
		assert b.get(Direction.getInverseDirection(AtoB))==a;
	}

	public PairVirtuals(RealNode a, RealNode b) {
		this(a, b, null);
	}
	//生成虚拟节点
	public List<VirtualNode>  generatorVirtualNode(){
		assert a.get(AtoB)==b;
		assert b.get(Direction.getInverseDirection(AtoB))==a;
		
		assert a.isReal();
		assert b.isReal();
		
		  nearA=new VirtualNodeImpl(required, a);
		  nearB=new VirtualNodeImpl(required, b);
		nearA.setPair(nearB);
		nearB.setPair(nearA);
		
		a.put(AtoB, nearA);
		nearA.put(Direction.getInverseDirection(AtoB), a);
		
		b.put(Direction.getInverseDirection(AtoB), nearB);
		nearB.put(AtoB, b);
		
		List<VirtualNode> list=new ArrayList<>(2);
		list.add(nearA);
		list.add(nearB);
		return list;
	}
	
	public void destroy(){
		a.put(AtoB, b);
		b.put(Direction.getInverseDirection(AtoB), a);
	}

	public RealNode getA() {
		return a;
	}

	public void setA(RealNode a) {
		this.a = a;
	}

	public RealNode getB() {
		return b;
	}

	public void setB(RealNode b) {
		this.b = b;
	}

	public VirtualNode getNearA() {
		return nearA;
	}

	public void setNearA(VirtualNode nearA) {
		this.nearA = nearA;
	}

	public VirtualNode getNearB() {
		return nearB;
	}

	public void setNearB(VirtualNode nearB) {
		this.nearB = nearB;
	}
	
	
	
	

}
