package com.zhangyin.saodi.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zhangyin.saodi.base.LevelMap;

public class MyJFrame extends JFrame{
	
	
	LevelMap levelMap;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyJFrame(LevelMap levelMap) throws HeadlessException {
		super();
		this.levelMap = levelMap;
		 this.setLayout(new BorderLayout());
		 this.setVisible(true);
		 setLocationRelativeTo(null);
		
		 this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		 Toolkit kit = Toolkit.getDefaultToolkit();
	     Dimension dimension = kit.getScreenSize();
	     int windowWidth=levelMap.col*20;
	     int windowHeight=levelMap.row*20;
	     this.setBounds(0, 0, windowWidth,windowHeight);
	     int screenWidth = dimension.width; //获取屏幕的宽
		 int screenHeight = dimension.height; //获取屏幕的高
		 this.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2 - windowHeight / 2);
		 this.add(contentPanel(),BorderLayout.CENTER);
		 this.repaint();
		 this.getContentPane().repaint();
	}
	
	public  JPanel  contentPanel(){
		JPanel jpanel=new JPanel();
		GridLayout gridLayout = new GridLayout(levelMap.row, levelMap.col,1,1);
		jpanel.setLayout(gridLayout);
		for (int i = 0; i < levelMap.row; i++) {
			for (int j = 0; j < levelMap.col; j++) {
				jpanel.add(new RealNodePanel(levelMap.nodes[i][j]));
			}
		}
		return jpanel;
	}
	
	

}
