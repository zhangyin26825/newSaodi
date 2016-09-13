package com.zhangyin.saodi.base;

import com.zhangyin.saodi.accesspoint.PairVirtuals;

public abstract class VirtualNode extends AbstractNode{
	
	
	public PairVirtuals pairVirtuals;
	//每个虚拟节点都对应一个真实节点
	public abstract RealNode  getRealNode();
	//虚拟节点是成对存在的，
	public abstract VirtualNode getPair();
	
	public abstract void setPair(VirtualNode v);
	//是否是必需的，由两度节点产生的虚拟节点是必需的
	//由三度节点产生的节点 不是必需的
	public abstract boolean isRequire();
	@Override
	public boolean isAccessPoint(){
		return true;
	}
	@Override
	public void setAccessPoint(boolean bool){
		throw new IllegalStateException("虚拟节点不能设置AccessPoint");
	}

	@Override
	public boolean isReal() {
		return false;
	}
	/** 虚拟节点  判断 是否结束分情况，如果ismarked为true,则标记为true,
	 * 如果没有，需要判断这个节点是否是必需的，如果不是，也返回true
	 */
	@Override
	public boolean isFinish() {
		if(isMarked()){
			return true;
		}
		if(!isRequire()){
			return true;
		}
		return false;
	}

	@Override
	public int getRowNum() {
		return getRealNode().getRowNum();
	}

	@Override
	public int getColNum() {
		return getRealNode().getColNum();
	}
	public PairVirtuals getPairVirtuals() {
		return pairVirtuals;
	}
	public void setPairVirtuals(PairVirtuals pairVirtuals) {
		this.pairVirtuals = pairVirtuals;
	}
	
	

	
	
	
}
