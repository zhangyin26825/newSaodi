package com.zhangyin.saodi.ui;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JPanel;

import com.zhangyin.saodi.area.Area;
import com.zhangyin.saodi.base.AbstractNode;

public class AreaPanel extends JPanel {

	Area area;
	int maxrow;
	int minrow;
	int maxcol;
	int mincol;
	RealNodePanel[][] panels;

	public AreaPanel(Area area) {
		super();
		this.area = area;
		maxrow = area.getAreaNode().stream().filter(n -> n.isReal()).mapToInt(n -> n.getRowNum()).max().getAsInt();
		minrow = area.getAreaNode().stream().filter(n -> n.isReal()).mapToInt(n -> n.getRowNum()).min().getAsInt();
		maxcol = area.getAreaNode().stream().filter(n -> n.isReal()).mapToInt(n -> n.getColNum()).max().getAsInt();
		mincol = area.getAreaNode().stream().filter(n -> n.isReal()).mapToInt(n -> n.getColNum()).min().getAsInt();
		int rowlength=maxrow - minrow+1;
		int collength=maxcol - mincol+1;
		panels = new RealNodePanel[rowlength][collength];
		GridLayout gridLayout = new GridLayout(rowlength, collength);
		this.setLayout(gridLayout);
		Set<AbstractNode> areaNode = area.getAreaNode();
		for (Iterator iterator = areaNode.iterator(); iterator.hasNext();) {
			AbstractNode abstractNode = (AbstractNode) iterator.next();
			if (abstractNode.isReal()) {
				int rowNum = abstractNode.getRowNum();
				int colNum = abstractNode.getColNum();
				RealNodePanel realNodePanel = new RealNodePanel(abstractNode);
				panels[rowNum - minrow][colNum - mincol] = realNodePanel;
			}
			System.out.println(abstractNode.getRowNum()+"       "+abstractNode.getColNum());
		}
		for (int i = 0; i < rowlength; i++) {
			for (int j = 0; j < collength; j++) {
				if(panels[i][j]==null){
					this.add(new RealNodePanel());
				}else{
					this.add(panels[i][j]);
				}	
			}
		}
		
	}

	public void paint(Graphics g) {
		super.paint(g);

	}

}
