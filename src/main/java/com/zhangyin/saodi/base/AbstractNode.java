package com.zhangyin.saodi.base;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * 地图的一个节点，这里定义为一个抽象类，需要派生出两个类，
 * 一个是 真实的节点，
 * 一个是虚拟的节点，
 * 主要意图在于 
 *  在真实的节点之间插入虚拟的节点   通过虚拟的节点把整个地图分割成若干个小的地图
 *  这里需要的考虑的是，应该在哪些真实节点之间插入虚拟节点    真实节点的  isAccessPoint字段就是用来判断是否在当前节点插入虚拟节点  
 *  判断当前节点 是 isAccessPoint ，然后怎么插入虚拟节点，又是另一个要考虑的问题，等做到的时候再说吧
 *  切割为若干个小图之后，具体怎么做，还不清晰
 *  
 *  需要指出的一点是，任何节点，除了起点和终点之外，2度的节点 两条边是一定要经过的，从一条边进入，然后从另一条边出去，
 * 										3度和4度，都要舍弃一些边
 * 我们需要考虑的是 从2度的节点，进行合理的推理，来猜测 行走的路线，与起始点的位置，
 * 但是整个页面不可能有一堆2度的节点，切割成若干个页面，所以需要加上特殊的3度节点的组合来实现。
 * 思路是往这方面走，怎么做就看情况了
 * 
 * 整体的原则 其实是基于 levelMap 的一种搜索的算法
 * AbstractNode 节点的设计 用于levelMap的搜索
 * 所有的属性与方法都只是用于搜索
 * 
 * 在子节点 的 真实节点  和虚拟节点之间，把判断  isAccessPoint  插入虚拟节点 这样的与搜索无关的业务，隔离在子类里，
 * 
 * @author Administrator
 *
 */
public  abstract class AbstractNode implements Map<Direction,AbstractNode>{
	//这个字段主要用来在图搜索的时候，标识当前节点是否已经经过了。
	private boolean marked=false;
	//当前节点 在某个方向上 连接的其他节点
	private Map<Direction,AbstractNode> moves;
	
	public Set<Direction> getcanMoveDirection(){
		Set<Direction> collect = moves.keySet().stream().filter(n->!moves.get(n).isMarked()).collect(Collectors.toSet());	
		return collect;
	}
	
	/**节点的类型，子类去实现 现在只有两种节点  真实节点 和  虚拟节点
	 * 真实节点 是根据 地图里具体的点转化存在的
	 * 虚拟节点 是根据一种规则，查询真实节点，在真实节点之间插入虚拟节点，根据虚拟节点，把整个地图分割成若干个小图
	 * 虚拟节点是成对存在的，在两个真实节点增加  两个虚拟节点，每个虚拟节点各对应一个真实节点，
	 * 虚拟节点之间不互相连接   见put方法   虚拟节点会有一个getPair()方法，得到与之对应的虚拟节点
	 */
	public abstract boolean isReal();
	/**
	 *  finish是判断在搜索中，是否结束的标识  
	 *  真实节点  只要marked为true，即可
	 *  虚拟节点  需要分情况    虚拟节点有两种情况，必需的和非必需的，必需的是一定要过的，非必需的则不一定
	 *  
	 * @return
	 */
	public abstract boolean isFinish();
	
	/**关于行号跟列号   真实节点本身就有行号跟列号
	 * 虚拟节点会绑定一个真实节点，所以虚拟节点，就用绑定的真实节点的信息
	 * 
	 * @return
	 */
	public abstract int getRowNum();
	
	public abstract int getColNum();
	
	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public	AbstractNode(){
    	//总结一个问题，这里如果不用LinkedHashMap  用hashMap 会造成debug模式跟 正常运行模式运行的结果不一样 
    	//hashMap 取key的顺序是不定的，搜索两度的节点，设置AccessPoint可能使设置方位出错，因为有些随机取一条边 使整个地图的切割不一致
    	moves=new LinkedHashMap<Direction, AbstractNode>(4);
	}

	public int size() {
		return moves.size();
	}

	public boolean isEmpty() {
		return moves.isEmpty();
	}

	public boolean containsKey(Object key) {
		return moves.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return moves.containsValue(value);
	}

	public AbstractNode get(Object key) {
		return moves.get(key);
	}

	public AbstractNode put(Direction key, AbstractNode value) {
		if(!this.isReal()&&!value.isReal()){
			throw new IllegalArgumentException("虚拟节点不能连接虚拟节点");
		}
		return moves.put(key, value);
	}

	public AbstractNode remove(Object key) {
		return moves.remove(key);
	}

	public void putAll(Map<? extends Direction, ? extends AbstractNode> m) {
		throw new IllegalArgumentException("不会用到 putAll方法");
	}
	public void clear() {
		throw new IllegalArgumentException("不会用到 clear方法");
	}
	public Set<Direction> keySet() {
		return moves.keySet();
	}

	public Collection<AbstractNode> values() {
		return moves.values();
	}

	public Set<java.util.Map.Entry<Direction, AbstractNode>> entrySet() {
		return moves.entrySet();
	}
	public abstract boolean isAccessPoint();
	public abstract void setAccessPoint(boolean b); 
	

}
