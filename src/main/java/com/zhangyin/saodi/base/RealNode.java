package com.zhangyin.saodi.base;

public class RealNode extends AbstractNode{
	
	private int rowNum; //所在的行
	private int colNum; //所在的列
	
	private boolean isAccessPoint;

	@Override
	public boolean isReal() {
		return true;
	}
	
	@Override
	public boolean isFinish() {
		return isMarked();
	}

	@Override
	public int getRowNum() {
		return rowNum;
	}

	@Override
	public int getColNum() {
		return colNum;
	}

	public boolean isAccessPoint() {
		return isAccessPoint;
	}

	public void setAccessPoint(boolean isAccessPoint) {
		this.isAccessPoint = isAccessPoint;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public void setColNum(int colNum) {
		this.colNum = colNum;
	}

	public RealNode(int rowNum, int colNum) {
		super();
		this.rowNum = rowNum;
		this.colNum = colNum;
	}

	@Override
	public String toString() {
		return "RealNode [rowNum=" + rowNum + ", colNum=" + colNum + "]";
	}
	
	
	

	
	

}
