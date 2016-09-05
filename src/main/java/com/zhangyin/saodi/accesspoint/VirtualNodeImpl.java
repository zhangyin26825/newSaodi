package com.zhangyin.saodi.accesspoint;

import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

public class VirtualNodeImpl  extends  VirtualNode{
	
	private boolean  require;
	
	private  RealNode realnode;
	
	private  VirtualNode virtualNode;
	
	public VirtualNodeImpl(boolean require, RealNode realnode) {
		super();
		this.require = require;
		this.realnode = realnode;
	}

	@Override
	public RealNode getRealNode() {
		return realnode;
	}

	@Override
	public VirtualNode getPair() {
		return virtualNode;
	}

	@Override
	public boolean isRequire() {
		return require;
	}

	public VirtualNode getVirtualNode() {
		return virtualNode;
	}

	public void setVirtualNode(VirtualNode virtualNode) {
		this.virtualNode = virtualNode;
	}

	@Override
	public void setPair(VirtualNode v) {
		this.virtualNode = v;
	
	}

	@Override
	public String toString() {
		return "VirtualNodeImpl [realnode=" + realnode + "]";
	}

	
	
	

}
