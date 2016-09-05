package com.zhangyin.saodi.accesspoint;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;
import com.zhangyin.saodi.base.RealNode;

/**
 * 判断一个节点能不能作为一个出入点 判断一个节点是不是出入点 暂定的判定逻辑如下
 * 当前节点周围没有没有出入点，如果当前节点连接的节点中，已经有出入点了，则当前节点不是出入点
 * 
 * 如果当前节点的度是2，判断连接的两个节点的度数，如果两个节点的度数都是2，则不是，其余情况下都是
 * 成对的判断，暂时还有点问题，如果连接的两个节点的度数中，有一个2，例如2,3或者2,4，成对的判断，以当前节点，跟不是2的节点 作为一个成对的出入点
 * 如果连接的两个节点的度数都大于2，例如，3，3 3,4 4,4 这种情况下，这里比较乱，再定义吧 两个节点还有一种特殊的情况要排除，ABCD 四个节点
 * 两行两列方式排列，以某一个角为例，假设A是左上角，那么A只有右和下两条边，A只有两度，A连接的B和C 同时 B和C都和D相连，这种情况下，A
 * 不能作为一个出入点，如果一个两度的节点的两条边是相对对应的话，就可以排除这种情况，两条边相邻的才会有这种问题,如果D是障碍物的话，就可以
 * 如果当前节点的度是3，则判断连接的节点中度为3的节点，这是两个度为3的节点，互相连接的情况，这两个度为3的节点，剩下的那个方向，是不是互相对应， 例如
 * AB两个节点上下相连 A在下，B在上，A节点除了上，还有右和下，B节点除了下，还有左和上， 那么 A剩下没有的方式是左， B剩下没有的方向是右，
 * 左右互相对应，所以AB可以作为一组出入点
 * 
 * 这里需要注意的是 2度的节点产生的出入点，在整个游戏路径中是一定要经过的
 * 3度的节点产生的出入点，是不一定需要的，以上面的AB为例，从A的右边到A，然后往下走， 然后经过一些路径之后，可以从B的左边到达B，然后再从B的上方走。
 */
public class AccessPointJudge {

	public boolean isAccessPoint(RealNode n) {
		// 这个节点在某些情况下，可能已经变成了accessPoint
		if (n.isAccessPoint()) {
			return false;
		}
		// 判断一个节点周围有没有出入点 这里为了防止 太多的出入点相连
		// 障碍物比较多的地方，三三组合的会比较多，同一个节点会有多种三三的组合，各种冲突，所以这里要限制一下
		boolean aroundhasAccessPoint = aroundhasAccessPoint(n);
		if (n.size() == 2) {
			if (aroundhasAccessPoint) {
				return false;
			}
			return twoDegreeNodeisAccessPoint(n);
		} else if (n.size() == 3) {
			return threeDegreeNodeisAccessPoint(n);
		}
		throw new IllegalArgumentException("isAccessPoint degree out");
	}

	// 判断一个三度的节点是不是出入点
	private boolean threeDegreeNodeisAccessPoint(RealNode n) {
		Set<Direction> keySet = n.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			Direction direction = (Direction) iterator.next();
			AbstractNode abstractNode = n.get(direction);
			if (abstractNode.size() == 3 &&
			// !aroundhasAccessPoint(n)&&
					isThreeDegreeAccessPoint(n, abstractNode, direction) && !abstractNode.isAccessPoint()) {
				// FIXME 这里对三度节点进行 条件判断， 第二条，需不需要这样，其实不确定，

				return true;
			}
		}

		return false;
	}

	// 判断一个两度的节点是不是出入点
	private boolean twoDegreeNodeisAccessPoint(RealNode n) {
		assert n.size() == 2;
		// 如果当前节点连接的两个节点的度数都是2的话，当前节点不是AccessPoint
		boolean allMatch = n.values().stream().allMatch(an -> an.size() == 2);
		if (allMatch) {
			return false;
		}
		// 然后需要判断的是那种ABCD的特殊情况

		Map<AbstractNode, Long> collect = n.values().stream().flatMap(an -> an.values().stream())
				.collect(Collectors.groupingBy(cn -> cn, Collectors.counting()));
		// 上面的方法去的取 与N相连的节点相连的所有节点，统计节点的个数
		// 所以肯定包含 n节点，又因为是与n相连，所以n肯定会出现两次
		assert collect.containsKey(n);
		assert collect.get(n).intValue() == 2;

		collect.remove(n);
		boolean anyMatch = collect.values().stream().anyMatch(l -> l.intValue() == 2);
		// 移除掉当前节点，判断是否还有统计结果为2的数据，有的话，证明ABCD那种情况出现了，没有的话，就没有
		if (anyMatch) {
			return false;
		}
		// 暂时没有其他情况，到了这里，就判断可以作为出入点了
		return true;
	}

	// 判断一个节点周围有没有出入点
	private boolean aroundhasAccessPoint(RealNode n) {
		return n.values().stream().anyMatch(an -> an.isAccessPoint());
	}

	public PairVirtuals getThreeOfPair(RealNode n) {
		Set<Direction> keySet = n.keySet();
		for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
			Direction direction = (Direction) iterator.next();
			AbstractNode abstractNode = n.get(direction);
			if (abstractNode.size() == 3 &&
			// !aroundhasAccessPoint(n)&&
					isThreeDegreeAccessPoint(n, abstractNode, direction) && !abstractNode.isAccessPoint()) {
				// FIXME 这里对三度节点进行 条件判断， 第二条，需不需要这样，其实不确定，
				PairVirtuals p=new PairVirtuals(n, (RealNode)abstractNode);
				return p;
			}
		}
		return null;
	}

	public static boolean isThreeDegreeAccessPoint(AbstractNode n, AbstractNode node, Direction d) {
		Set<Direction> nSet = n.keySet();
		assert nSet.size() == 3;
		Set<Direction> nodeSet = node.keySet();
		assert nodeSet.size() == 3;

		Direction nlackofDirection = Direction.lackofDirection(nSet);
		Direction nodelackofDirection = Direction.lackofDirection(nodeSet);

		Set<Direction> temp = new HashSet<Direction>();
		temp.add(nodelackofDirection);
		temp.add(nlackofDirection);
		temp.add(d);
		temp.add(Direction.getInverseDirection(d));
		if (!Direction.isFullDirection(temp)) {
			return false;
		}
		return Direction.isPair(nlackofDirection, nodelackofDirection);
	}
}
