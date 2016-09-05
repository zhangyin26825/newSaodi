package com.zhangyin.saodi.area;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;
/***
 * 建立节点与区域之间的映射关系，
 * 能根据一个节点找到这个节点的区域
 * 虚拟节点也是一样，虚拟节点使用成对的机制，在两个区域的联合处，有两个虚拟节点，分别属于不同的区域
 * @author Administrator
 *
 */
public class NodeAreaMapping {
	
	Map<AbstractNode,Area> map;
	
	public Area get(AbstractNode node){
		return map.get(node);
	}

	public NodeAreaMapping(List<Area> areas) {
		map=new HashMap<>();
		for (Iterator iterator = areas.iterator(); iterator.hasNext();) {
			Area area = (Area) iterator.next();
			Set<AbstractNode> realnodes = area.realnodes;
			for (Iterator iterator2 = realnodes.iterator(); iterator2.hasNext();) {
				RealNode realNode = (RealNode) iterator2.next();
				assert !map.containsKey(realNode);
				map.put(realNode, area);
			}
			Set<AbstractNode> virtualNodes = area.virtualNodes;
			for (Iterator iterator2 = virtualNodes.iterator(); iterator2.hasNext();) {
				VirtualNode virtualNode = (VirtualNode) iterator2.next();
				assert !map.containsKey(virtualNode);
				map.put(virtualNode, area);
			}	
		}
	}

	public Map<AbstractNode, Area> getMap() {
		return map;
	}

	public void setMap(Map<AbstractNode, Area> map) {
		this.map = map;
	}
	
	
	
	

}
