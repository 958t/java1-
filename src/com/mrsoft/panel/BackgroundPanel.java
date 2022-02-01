package com.mrsoft.panel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel { // 背景面板类
    
    private Image image; // 背景图片
    
    public BackgroundPanel() { // 构造方法
        super(); // 调用JPanel的构造函数
        setOpaque(false); // 组件允许控件下面的像素显现出来
        setLayout(null); // 绝对布局
    }
    
    public void setImage(Image image) { // 设置图片
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) { // 重写绘制组件外观
        if (image != null) {
            int width = getWidth(); // 获取组件的宽度
            int height = getHeight(); // 获取组件的高度
            g.drawImage(image, 0, 0, width, height, this); // 绘制图片与组件大小相同
        }
        super.paintComponent(g); // 执行超类方法
    }
}
