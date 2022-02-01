package com.mrsoft.panel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel { // ���������
    
    private Image image; // ����ͼƬ
    
    public BackgroundPanel() { // ���췽��
        super(); // ����JPanel�Ĺ��캯��
        setOpaque(false); // �������ؼ�������������ֳ���
        setLayout(null); // ���Բ���
    }
    
    public void setImage(Image image) { // ����ͼƬ
        this.image = image;
    }
    
    @Override
    protected void paintComponent(Graphics g) { // ��д����������
        if (image != null) {
            int width = getWidth(); // ��ȡ����Ŀ��
            int height = getHeight(); // ��ȡ����ĸ߶�
            g.drawImage(image, 0, 0, width, height, this); // ����ͼƬ�������С��ͬ
        }
        super.paintComponent(g); // ִ�г��෽��
    }
}
