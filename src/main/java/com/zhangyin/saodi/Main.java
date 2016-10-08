package com.zhangyin.saodi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zhangyin.saodi.accesspoint.VirtualNodeGenerator;
import com.zhangyin.saodi.area.Area;
import com.zhangyin.saodi.area.AreaFactory;
import com.zhangyin.saodi.area.NoStartAreaAnswer;
import com.zhangyin.saodi.area.NodeAreaMapping;
import com.zhangyin.saodi.base.LevelMap;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;
import com.zhangyin.saodi.ui.AreaPanel;
import com.zhangyin.saodi.ui.DemoFrame;
import com.zhangyin.saodi.ui.MyJFrame;

public class Main {

	public static void main(String[] args) {
		//level=72&x=27&y=27&map=000000010000110000100001000000011000000110010000100010100001101000111001010000010000101100010100101001100010010000000010000101101100010011000100000000101100000010001000001100000100011000010100001001111110000011000010100101000011111000001000000000001011000010000100110001011000011000000111110000100000011110000101111100010000000000000100001100001000011000000000100000100000101100110011000000000110010001000000011001000000000010011010011011100011111111000010000011000000001111110001100101000000010100000010110001001100001000100100010000001000101100001111100000001001110100000100100000000000000100111000000100110000110110001000011000010000100010011000000110001001000001011010010000010100001001000001000110001000111111100000100000000

		
				Integer row=27;
				Integer col=27;
				int level=72; 
				String map="000000010000110000100001000000011000000110010000100010100001101000111001010000010000101100010100101001100010010000000010000101101100010011000100000000101100000010001000001100000100011000010100001001111110000011000010100101000011111000001000000000001011000010000100110001011000011000000111110000100000011110000101111100010000000000000100001100001000011000000000100000100000101100110011000000000110010001000000011001000000000010011010011011100011111111000010000011000000001111110001100101000000010100000010110001001100001000100100010000001000101100001111100000001001110100000100100000000000000100111000000100110000110110001000011000010000100010011000000110001001000001011010010000010100001001000001000110001000111111100000100000000";
//				int row=2;
//				int col=2;
//				int level=2;
//				String map="0001";		
				
				LevelMap levelmap=new LevelMap(map, level, row, col);
				Set<RealNode> nodesets = levelmap.nodesets;
				System.out.println("真实节点的数量"+nodesets.size());
				for (Iterator iterator = nodesets.iterator(); iterator.hasNext();) {
					RealNode realNode = (RealNode) iterator.next();
					//System.out.println(realNode.getColNum()+"　　　　"+realNode.getColNum()+"  "+realNode.degree());
				}
				//根据地图信息，判断出所有的AccessPoint节点，生成虚拟节点，把虚拟节点插入到真实的节点之中
				VirtualNodeGenerator vmg=new VirtualNodeGenerator(levelmap);
				//得到所有的 虚拟节点
				List<VirtualNode> virtualNodes = vmg.getVirtualNodes();
				System.out.println("虚拟节点的数量"+virtualNodes.size());
				
				MyJFrame frame=new  MyJFrame(levelmap);
				
				AreaFactory factory=new AreaFactory(virtualNodes,vmg.getPairs());
				List<Area> areas = factory.generatorArea();
				NodeAreaMapping mapping=new NodeAreaMapping(areas);
				for (Iterator iterator = areas.iterator(); iterator.hasNext();) {
					Area area = (Area) iterator.next();
					System.out.print(area.getSolves().size()+"  ");	
					if(area.getSolves().size()==1){
						NoStartAreaAnswer noStartAreaAnswer = area.getSolves().get(0);
						{
							Set<VirtualNode> from = noStartAreaAnswer.getFrom();
							for (Iterator iterator2 = from.iterator(); iterator2.hasNext();) {
								VirtualNode virtualNode = (VirtualNode) iterator2.next();
								Area area2 = mapping.get(virtualNode.getPair());
								List<NoStartAreaAnswer> solves = area2.getSolves();
								for (Iterator iterator3 = solves.iterator(); iterator3.hasNext();) {
									NoStartAreaAnswer noStartAreaAnswer2 = (NoStartAreaAnswer) iterator3.next();
									if(!noStartAreaAnswer2.getTo().contains(virtualNode.getPair())){
										noStartAreaAnswer2.setDel(true);
									}
								}
								
							}
						}
						
						Set<VirtualNode> to = noStartAreaAnswer.getTo();
						for (Iterator iterator2 = to.iterator(); iterator2.hasNext();) {
							VirtualNode virtualNode = (VirtualNode) iterator2.next();
							Area area2 = mapping.get(virtualNode.getPair());
							List<NoStartAreaAnswer> solves = area2.getSolves();
							for (Iterator iterator3 = solves.iterator(); iterator3.hasNext();) {
								NoStartAreaAnswer noStartAreaAnswer2 = (NoStartAreaAnswer) iterator3.next();
								if(!noStartAreaAnswer2.getFrom().contains(virtualNode.getPair())){
									noStartAreaAnswer2.setDel(true);
								}
							}
							
						}	
					}
				}
				
				System.out.println();
				System.out.println();
				for (Iterator iterator = areas.iterator(); iterator.hasNext();) {
					Area area = (Area) iterator.next();
					System.out.print(area.getSolves().stream().filter(n->!n.isDel()).count()+"  ");
				}
				
				
//				List<Area>  zeroSolvesArea=new ArrayList<>();
//				
//				// List<Area> zeroSolvesArea = areas.stream().filter(a->a.getVirtualNodes().size()==2).collect(Collectors.toList());
//				
//				System.out.println("区域的数量为"+areas.size());
//				for (Iterator iterator = areas.iterator(); iterator.hasNext();) {
//					Area area = (Area) iterator.next();
//					System.out.println("该区域的无起点解法有 "+area.getSolves().size());
//					if(area.getSolves().size()==1){
//						zeroSolvesArea.add(area);
//					}
//					area.clear();
//				}
//				System.out.println("无起点解法的区域数量为"+zeroSolvesArea.size());
//				Area area = zeroSolvesArea.get(6);
//				System.out.println("区域的真实节点数量为"+area.getRealnodes().size());
//				System.out.println("区域的虚拟节点数量为"+area.getVirtualNodes().size());
//				AreaPanel panel=new AreaPanel(area);
//				DemoFrame demoFrame=new DemoFrame(panel);
//				area.clear();
//				area.chooseVirtualNodes(true);
				
//				NodeAreaMapping nam=new NodeAreaMapping(areas);
//				System.out.println("节点区域映射中虚拟节点的数量为"+nam.getMap().keySet().stream().filter(n->!n.isReal()).count());
	
		
	}

}
