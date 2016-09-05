package com.zhangyin.saodi.base;

import java.util.HashSet;
import java.util.Set;
/***
 * 方向，节点之间的连接
 * @author Administrator
 *
 */
public enum Direction {
	Up, Left, Down, Right;

	public String toString() {
		if (this.equals(Up)) {
			return "u";
		}
		if (this.equals(Left)) {
			return "l";
		}
		if (this.equals(Down)) {
			return "d";
		}
		if (this.equals(Right)) {
			return "r";
		}
		throw new IllegalAccessError("不合法的方向类型");
		// return null;
	};

	public static Direction getInverseDirection(Direction d) {
		switch (d) {
		case Up:
				return Down;
		case Down:
				return Up;
		case Left:
				return Right;
		case Right:
				return Left;
		default:
			throw new IllegalAccessError("不合法的方向类型");
		}
	}
	

	 //判断方向 是不是一对  比如   上下是一对  左右是一对
	 public static  boolean isPair(Set<Direction> set){
		 if(set.size()!=2){
			 throw new IllegalAccessError("不合适 集合类型，数量应当为2,才能判断方向是不是相对"); 
		 }
		 if(set.contains(Direction.Down)&&set.contains(Direction.Up)){
			 return true;
		 }
		 if(set.contains(Direction.Left)&&set.contains(Direction.Right)){
			 return true;
		 }
		 return false;
	 }
	 //查找三个方向里，缺少的那个
	 public static Direction lackofDirection(Set<Direction> set){
		 if(set.size()!=3){
			 throw new IllegalAccessError("不合适 集合类型，数量应当为3,才能判断出缺少的那个方向"); 
		 }
		 if(!set.contains(Up)){
			 return Up;
		 }
		 else if(!set.contains(Down)){
			 return Down;
		 }
		 else if(!set.contains(Left)){
			 return Left;
		 }else  if(!set.contains(Right)){
			 return Right;
		 }else{
			 //throw new IllegalAccessError("不合适 集合类型，数量应当为3,才能判断出缺少的那个方向"); 
			 return null;
		 }
		 
	 }
	 
	 public static boolean isPair(Direction d1,Direction d2){
		 Set<Direction> set=new HashSet<Direction>(2);
		 set.add(d2);
		 set.add(d1);
		 if(set.size()!=2){
			 return false;
		 }
		 return isPair(set);
	 }
	 
	 public static Direction [] toArray(Set<Direction> keySet){
		 Direction [] result=new Direction[keySet.size()];
		 int i=0;
		 if(keySet.contains(Up)){
			 result[i]=Up;
			 i++;
		 }
		 if(keySet.contains(Down)){
			 result[i]=Down;
			 i++;
		 }
		 if(keySet.contains(Left)){
			 result[i]=Left;
			 i++;
		 }
		 if(keySet.contains(Right)){
			 result[i]=Right;
			 i++;
		 }
		 return result;
	 }
	 
	 public static boolean isFullDirection(Set<Direction> keySet){
		 if(keySet.contains(Up)&&keySet.contains(Down)&&keySet.contains(Left)&&keySet.contains(Right)){
			 return true;
		 }
		 return false;
	 }
	 
//	 public static boolean isThreeDegreeAccessPoint(AbstractNode n,AbstractNode node,Direction d){
//		 Set<Direction> nSet = n.directions();
//		 assert nSet.size()==3;
//		 Set<Direction> nodeSet = node.directions();
//		 assert nodeSet.size()==3;
//		 
//		 Direction nlackofDirection = lackofDirection(nSet);
//		 Direction nodelackofDirection = lackofDirection(nodeSet);
//		 
//		 Set<Direction> temp=new HashSet<Direction>();
//		 temp.add(nodelackofDirection);
//		 temp.add(nlackofDirection);
//		 temp.add(d);
//		 temp.add(getInverseDirection(d));
//		 if(!isFullDirection(temp)){
//			 return false;
//		 }
//		 return isPair(nlackofDirection, nodelackofDirection); 
//	 }

}
