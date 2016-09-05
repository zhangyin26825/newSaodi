package com.zhangyin.saodi.accesspoint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;
import com.zhangyin.saodi.base.LevelMap;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

public class VirtualNodeGenerator {

	Set<RealNode> twoDegreeNodes;

	Set<RealNode> threeDegreeNodes;
	
	AccessPointJudge judge;
	
	List<VirtualNode> virtualNodes;
	
	List<PairVirtuals> pairs;

	public VirtualNodeGenerator(LevelMap levelmap) {
		pairs=new ArrayList<>();
		twoDegreeNodes =new HashSet<>();
		twoDegreeNodes.addAll(levelmap.getTwoDegreeNodes());
		System.out.println("两度节点的数量"+twoDegreeNodes.size());
		threeDegreeNodes=new HashSet<>();
		threeDegreeNodes.addAll(levelmap.getThreeDegreeNodes());
		System.out.println("三度节点的数量"+threeDegreeNodes.size());
		judge=new AccessPointJudge();
		virtualNodes=new LinkedList<>();
		/**
		 * 找到两度的AccessPoint 然后插入虚拟节点
		 */
		initTwoDegree();
		System.out.println("两度的插入的虚拟节点的数目是"+virtualNodes.size());
		/**
		 * 找到三度的AccessPoint 然后插入虚拟节点
		 */
		initThreeDegree();
	}
	
	private void initThreeDegree() {
		for (Iterator iterator = threeDegreeNodes.iterator(); iterator.hasNext();) {
			RealNode realNode = (RealNode) iterator.next();
			boolean accessPoint = judge.isAccessPoint(realNode);
			if(accessPoint){
				PairVirtuals t=judge.getThreeOfPair(realNode);
				assert t!=null;
				//t.setAccessPoint(true);
				virtualNodes.addAll(t.generatorVirtualNode());
				pairs.add(t);
			}
		}
		
	}

	public void initTwoDegree(){
		for (Iterator iterator = twoDegreeNodes.iterator(); iterator.hasNext();) {
			RealNode realNode = (RealNode) iterator.next();
			boolean accessPoint = judge.isAccessPoint(realNode);
			if(accessPoint){
				realNode.setAccessPoint(true);
				generateTwoDegreeVirtualNode(realNode);
			}
			
		}	
	}
	
	//两度的accessPoint 插入虚拟节点  
	private void generateTwoDegreeVirtualNode(RealNode realNode){
		//随便选一个方向，
		Direction d=realNode.keySet().iterator().next();
		//这个方向上连接的节点
		AbstractNode another = realNode.get(d);
		another.setAccessPoint(true);
		assert  another.isReal();
		
		
		PairVirtuals p=new PairVirtuals(realNode, (RealNode)another);
		virtualNodes.addAll(p.generatorVirtualNode());
		pairs.add(p);
		
		//generatorVirtualNode(realNode, d, another);
	}
	
//	public void generatorVirtualNode(AbstractNode realNode, Direction d, AbstractNode another){
//		assert realNode.get(d)==another;
//		assert another.get(Direction.getInverseDirection(d))==realNode;
//		
//		assert realNode.isReal();
//		assert another.isReal();
//		
//		/** 在  realNode  和 another 两个节点之间，新增两个虚拟节点   
//		 *   realNodevirtual  为靠近  realnode的一端
//		 *   anothervirtual   为靠近 another的一端
//		 * 
//		 */
//		TwoDegreeVirtualNode realNodevirtual=new TwoDegreeVirtualNode(realNode,(RealNode)realNode);
//		TwoDegreeVirtualNode anothervirtual=new TwoDegreeVirtualNode(realNode,(RealNode)another);
//		
//		VirtualNode realNodevirtual=new VirtualNodeImpl();
//		
//		realNodevirtual.setPair(anothervirtual);
//		anothervirtual.setPair(realNodevirtual);
//		
//		
//		//realNodevirtual.put(d, anothervirtual);
//		realNodevirtual.put(Direction.getInverseDirection(d), realNode);
//		
//		anothervirtual.put(d, another);
//		//anothervirtual.put(Direction.getInverseDirection(d), realNodevirtual);
//		
//		realNode.put(d, realNodevirtual);
//		another.put(Direction.getInverseDirection(d), anothervirtual);
//		
//
//		virtualNodes.add(realNodevirtual);
//		virtualNodes.add(anothervirtual);	
//		assert realNode.get(d)==realNodevirtual;
//		//assert realNodevirtual.get(d)==anothervirtual;
//		assert anothervirtual.get(d)==another;
//		
//		assert another.get(Direction.getInverseDirection(d))==anothervirtual;
//		//assert anothervirtual.get(Direction.getInverseDirection(d))==realNodevirtual;
//		assert realNodevirtual.get(Direction.getInverseDirection(d))==realNode;
//		
//		}	

	public List<VirtualNode> getVirtualNodes() {
		return virtualNodes;
	}

	public void setVirtualNodes(List<VirtualNode> virtualNodes) {
		this.virtualNodes = virtualNodes;
	}

	public List<PairVirtuals> getPairs() {
		return pairs;
	}

	public void setPairs(List<PairVirtuals> pairs) {
		this.pairs = pairs;
	}
	
	
	

}
