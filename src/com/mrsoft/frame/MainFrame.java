package com.mrsoft.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.mrsoft.panel.BackgroundPanel;
import com.mrsoft.source.Sentence;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame { // ������

	private JPanel contentPane; // �������
	private JTextField textField; // �ı���
	private JButton searchButton; // ������ť
	private JLabel noteLabel; // ���������塱��ǩ
	private JLabel wordLabel; // �����ʡ���ǩ
	private JLabel explainLabel; // �����塱��ǩ
	private String[][] data = new String[7989][2]; // ��Ӣ��������
	private JLabel dateLabel; // �����ڡ���ǩ
	private JLabel sentenceLabel; // ��Ӣ�ľ��ӡ���ǩ
	private JLabel lblTranslation; // ���������ġ���ǩ

	public static void main(String[] args) {
		MainFrame frame = new MainFrame(); // ����������
		frame.setVisible(true); // ʹ������ɼ�
	}

	public MainFrame() { // ������Ĺ��췽��
		setTitle("����Ӣ�뺺"); // ����������ı���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ����������Ĺرշ�ʽ
		setBounds(340, 150, 800, 500); // �����������λ������
		
		contentPane = new JPanel(); // �����������
		contentPane.setLayout(new BorderLayout(0, 0)); // �����������Ĳ���Ϊ�߽粼��
		setContentPane(contentPane); // ����������������������
		
		BackgroundPanel backgroundPanel = new BackgroundPanel(); // �����������
		backgroundPanel.setImage(getToolkit().getImage(getClass().getResource("/pic/main.png"))); // ���ñ�������ͼƬ
		contentPane.add(backgroundPanel, BorderLayout.CENTER); // �ѱ��������������������м�
		
		textField = new JTextField(); // �����ı���
		textField.getDocument().addDocumentListener(new DocumentListener() { // Ϊ�ı�������ı��¼�������
			@Override
			public void removeUpdate(DocumentEvent e) { // �ı�ɾ��ʱ
				do_textField_removeUpdate(e); // �ı�ɾ��ʱ����Ҫִ�еķ���
			}

			@Override
			public void insertUpdate(DocumentEvent e) { // �ı�����ʱ
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) { // �ı�����ʱ
				
			}
		});
		textField.addKeyListener(new KeyAdapter() { // Ϊ�ı�����Ӽ����¼�������
			public void keyTyped(KeyEvent e) { // ����ĳ����ʱ
				if (e.getKeyChar() == '\n') // ���µİ����ǻس�ʱ
					searchButton.doClick(); // ����������ťִ�е���¼�
			}
		});
		textField.setBounds(133, 82, 453, 50); // �����ı����λ������
		backgroundPanel.add(textField); // ���ı�����ӵ����������
		
		searchButton = new JButton(); // ��������������ť
		searchButton.addMouseListener(new MouseAdapter() { // Ϊ����������ť�������¼�������
			@Override
			public void mouseEntered(MouseEvent e) { // ������롰��������ťʱ
				do_searchButton_mouseEntered(e); // ������롰��������ťʱ����Ҫִ�еķ���
			}
			@Override
			public void mouseExited(MouseEvent e) { // ����Ƴ�����������ťʱ
				do_searchButton_mouseExited(e); // ����Ƴ�����������ťʱ����Ҫִ�еķ���
			}
		});
		searchButton.addActionListener(new ActionListener() { // Ϊ����������ť��Ӷ����¼�������
			public void actionPerformed(ActionEvent e) { // ����������ť��������ʱ
				do_searchButton_actionPerformed(e); // ����������ť��������ʱ����Ҫִ�еķ���
			}
		});
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/pic/btn0.jpg"))); // ���á���������ť��ͼ��
		searchButton.setBounds(585, 82, 52, 49); // ���á���������ť��λ������
		backgroundPanel.add(searchButton); // �ѡ���������ť��ӵ����������
		
		noteLabel = new JLabel(); // �������������塱��ǩ
		noteLabel.setForeground(new Color(51, 153, 204)); // ���á��������塱��ǩ��������ɫ
		noteLabel.setFont(new Font("����", Font.PLAIN, 16)); // ���á��������塱��ǩ��������ʽ
		noteLabel.setBounds(133, 147, 100, 30); // ���á��������塱��ǩ��λ������
		backgroundPanel.add(noteLabel); // �ѡ��������塱��ǩ��ӵ����������
		
		wordLabel = new JLabel(); // ���������ʡ���ǩ
		wordLabel.setFont(new Font("����", Font.BOLD, 20)); // ���á����ʡ���ǩ��������ʽ
		wordLabel.setBounds(133, 180, 200, 30); // ���á����ʡ���ǩ��λ������
		backgroundPanel.add(wordLabel); // �ѡ����ʡ���ǩ��ӵ����������
		
		explainLabel = new JLabel(); // ���������塱��ǩ
		explainLabel.setFont(new Font("����", Font.PLAIN, 20)); // ���á����塱��ǩ��������ʽ
		explainLabel.setBounds(133, 220, 300, 30); // ���á����塱��ǩ��λ������
		backgroundPanel.add(explainLabel); // �ѡ����塱��ǩ��ӵ����������
		
		dateLabel = new JLabel(); // ���������ڡ���ǩ
		dateLabel.setForeground(new Color(51, 153, 204)); // ���á����ڡ���ǩ��������ɫ
		dateLabel.setFont(new Font("����", Font.BOLD, 18)); // ���á����ڡ���ǩ��������ʽ
		Date date = new Date(); // ����Date�����
		String strDate = date + ""; // ��Date�����ת��ΪString����
		int spaceFirst = strDate.indexOf(" "); // ��õ�һ���ո������
		int spaceSecond = strDate.indexOf(" ", spaceFirst + 1); // ��õڶ����ո������
		int spaceThird = strDate.indexOf(" ", spaceSecond + 1); // ��õ������ո������
		String target = strDate.substring(spaceFirst + 1, spaceThird); // ͨ����ȡ�ַ���������·ݺ�����
		dateLabel.setText(target); // �ѡ����ڡ���ǩ���ı���������Ϊ�ѻ�õ��·ݺ�����
		dateLabel.setBounds(133, 260, 300, 30); // ���á����ڡ���ǩ��λ������
		backgroundPanel.add(dateLabel); // �ѡ����ڡ���ǩ��ӵ����������
		
		String day = strDate.substring(spaceSecond + 1, spaceThird); // ͨ����ȡ�ַ������������
		Sentence sentence = new Sentence(); // ������������
		String strSentence = sentence.show(day); // �����ѻ�õ����ڣ���ȡ��Ӣ�ľ��ӡ��͡��������ġ�
		String[] str = strSentence.split("#"); // ʹ��"#"����ѻ�õġ�Ӣ�ľ��ӡ��͡��������ġ�
		
		sentenceLabel = new JLabel(); // ������Ӣ�ľ��ӡ���ǩ
		sentenceLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14)); // ���á�Ӣ�ľ��ӡ���ǩ��������ʽ
		sentenceLabel.setText("<html>" + str[0] + "</html>"); // ��Ӣ�ľ��ӡ���ǩ��ʾ��Ӣ�ľ��ӡ�
		sentenceLabel.setBounds(133, 295, 504, 40); // ���á�Ӣ�ľ��ӡ���ǩ��λ������
		backgroundPanel.add(sentenceLabel); // �ѡ�Ӣ�ľ��ӡ���ǩ��ӵ����������
		
		lblTranslation = new JLabel(); // �������������ġ���ǩ
		lblTranslation.setFont(new Font("����", Font.PLAIN, 14)); // ���á��������ġ���ǩ��������ʽ
		lblTranslation.setText("<html>" + str[1] + "</html>"); // ���������ġ���ǩ��ʾ���������ġ�
		lblTranslation.setBounds(133, 340, 504, 40); // ���á��������ġ���ǩ��λ������
		backgroundPanel.add(lblTranslation); // �ѡ��������ġ���ǩ��ӵ����������
	}

	protected void do_searchButton_mouseEntered(MouseEvent e) { // ������롰��������ťʱ
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/pic/btn1.jpg"))); // ���á���������ť��ͼ��
	}
	
	protected void do_searchButton_mouseExited(MouseEvent e) { // ����Ƴ�����������ťʱ
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/pic/btn0.jpg"))); // ���á���������ť��ͼ��
	}
	
	protected void do_searchButton_actionPerformed(ActionEvent e) { // ����������ť��������ʱ
		String word = textField.getText(); // ����ı����е��ı�����
		if (word.equals("")) { // ����ı����е��ı�����Ϊ""
			JOptionPane.showMessageDialog(null, "������Ҫ��ѯ�ĵ���", "����", JOptionPane.WARNING_MESSAGE); // ��������Ի���
		} else if (!word.matches("^[A-Za-z]+$")) { // ����ı����е��ı����ݲ���Ӣ����ĸ�����Դ�Сд��
			JOptionPane.showMessageDialog(null, "ע�⣺ֻ������Ӣ���ַ�����Сд���ޣ�", "����", JOptionPane.ERROR_MESSAGE); // ��������Ի���
		} else { // ����ı����е��ı�������Ӣ����ĸ�����Դ�Сд��
			readFile(); // ���ö�ȡ��������Դ�ļ��ķ���
			translation(); // ���÷��뷽��
		}
	}

	protected void do_textField_removeUpdate(DocumentEvent e) { // �ı����е��ı�ɾ��ʱ
		noteLabel.setText(""); // ���á��������塱��ǩ���ı�����Ϊ""
		wordLabel.setText(""); // ���á����ʡ���ǩ���ı�����Ϊ""
		explainLabel.setText(""); // ���á����塱��ǩ���ı�����Ϊ""
	}
	
	/**
	 * ����
	 */
	private void translation() {
		String text = textField.getText().trim(); // ��ȡ�ı����е�����
		String trans = getTrans(text); // �����ı����е����ݻ�ȡ��������
		if ("".equalsIgnoreCase(trans) || null == trans) { // ���������Ϊ��
			explainLabel.setText("����ľ�У��볢����������"); // �����塱��ǩ��ʾ������ľ�У��볢���������롱
		} else { // �����������Ϊ��
			noteLabel.setText("��������"); // ���������塱��ǩ��ʾ���������塱
			wordLabel.setText(textField.getText().toLowerCase()); // �����ʡ���ǩ��ʾ����
			explainLabel.setText(trans); // �����塱��ǩ��ʾ����
		}
	}

	/**
	 * ���ݵ������ݻ�ȡ��������
	 * 
	 * @param english
	 *            - ���ʵ���
	 * @return Ѱ�ҵ��ʵ��ʵĴ��壬�ҵ��򷵻أ�δ�ҵ��򷵻ص���ԭ����
	 */
	public String getTrans(String text) {
		for (int i = 0; i < data.length; i++) {// ������������
			if (data[i][0].equalsIgnoreCase(text)) {// �ж���û����ͬ�ĵ��ʵ���
				return data[i][1];// ���ص��ʵĴ���
			}
		}
		return "����ľ�У��볢����������";// ���򷵻������ԭ��
	}

	/**
	 * ��ȡ��������Դ�ļ�
	 */
	private void readFile() {
		BufferedReader br = null;// �����ַ�������
		try {
			// ��ȡ����ͬĿ¼�µ�����Դ�ļ�
			File dateFile = new File("src/words/source");
			// ����GBK�ַ������ȡ�ļ�
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dateFile), "GBK"));
			String tmp = ""; // ��ʱ�ַ�������
			for (int count = 0; (tmp = br.readLine()) != null; count++) { // ���ж�ȡ�ļ��е�����
				String[] a = tmp.split("#"); // ����#���Ž��зָ�
				data[count][0] = a[0]; // ����
				data[count][1] = a[1]; // ����
			}
		} catch (FileNotFoundException e) { // �����ļ�û�б��ҵ��쳣
			e.printStackTrace();
		} catch (IOException e) { // ����IO����/����쳣
			e.printStackTrace();
		}
	}
}
