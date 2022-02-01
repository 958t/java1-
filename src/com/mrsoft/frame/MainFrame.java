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

public class MainFrame extends JFrame { // 主窗体

	private JPanel contentPane; // 内容面板
	private JTextField textField; // 文本框
	private JButton searchButton; // 搜索按钮
	private JLabel noteLabel; // “基础释义”标签
	private JLabel wordLabel; // “单词”标签
	private JLabel explainLabel; // “词义”标签
	private String[][] data = new String[7989][2]; // 中英对照数据
	private JLabel dateLabel; // “日期”标签
	private JLabel sentenceLabel; // “英文句子”标签
	private JLabel lblTranslation; // “中文译文”标签

	public static void main(String[] args) {
		MainFrame frame = new MainFrame(); // 创建主窗体
		frame.setVisible(true); // 使主窗体可见
	}

	public MainFrame() { // 主窗体的构造方法
		setTitle("单词英译汉"); // 设置主窗体的标题
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置主窗体的关闭方式
		setBounds(340, 150, 800, 500); // 设置主窗体的位置与宽高
		
		contentPane = new JPanel(); // 创建内容面板
		contentPane.setLayout(new BorderLayout(0, 0)); // 设置内容面板的布局为边界布局
		setContentPane(contentPane); // 把内容面板放置在主窗体中
		
		BackgroundPanel backgroundPanel = new BackgroundPanel(); // 创建背景面板
		backgroundPanel.setImage(getToolkit().getImage(getClass().getResource("/pic/main.png"))); // 设置背景面板的图片
		contentPane.add(backgroundPanel, BorderLayout.CENTER); // 把背景面板放置在内容面板的中间
		
		textField = new JTextField(); // 创建文本框
		textField.getDocument().addDocumentListener(new DocumentListener() { // 为文本框添加文本事件监听器
			@Override
			public void removeUpdate(DocumentEvent e) { // 文本删除时
				do_textField_removeUpdate(e); // 文本删除时，需要执行的方法
			}

			@Override
			public void insertUpdate(DocumentEvent e) { // 文本插入时
				
			}

			@Override
			public void changedUpdate(DocumentEvent e) { // 文本更改时
				
			}
		});
		textField.addKeyListener(new KeyAdapter() { // 为文本框添加键盘事件监听器
			public void keyTyped(KeyEvent e) { // 键入某个键时
				if (e.getKeyChar() == '\n') // 按下的按键是回车时
					searchButton.doClick(); // “搜索”按钮执行点击事件
			}
		});
		textField.setBounds(133, 82, 453, 50); // 设置文本框的位置与宽高
		backgroundPanel.add(textField); // 把文本框添加到背景面板中
		
		searchButton = new JButton(); // 创建“搜索”按钮
		searchButton.addMouseListener(new MouseAdapter() { // 为“搜索”按钮添加鼠标事件监听器
			@Override
			public void mouseEntered(MouseEvent e) { // 鼠标移入“搜索”按钮时
				do_searchButton_mouseEntered(e); // 鼠标移入“搜索”按钮时，需要执行的方法
			}
			@Override
			public void mouseExited(MouseEvent e) { // 鼠标移出“搜索”按钮时
				do_searchButton_mouseExited(e); // 鼠标移出“搜索”按钮时，需要执行的方法
			}
		});
		searchButton.addActionListener(new ActionListener() { // 为“搜索”按钮添加动作事件监听器
			public void actionPerformed(ActionEvent e) { // “搜索”按钮发生动作时
				do_searchButton_actionPerformed(e); // “搜索”按钮发生动作时，需要执行的方法
			}
		});
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/pic/btn0.jpg"))); // 设置“搜索”按钮的图标
		searchButton.setBounds(585, 82, 52, 49); // 设置“搜索”按钮的位置与宽高
		backgroundPanel.add(searchButton); // 把“搜索”按钮添加到背景面板中
		
		noteLabel = new JLabel(); // 创建“基础释义”标签
		noteLabel.setForeground(new Color(51, 153, 204)); // 设置“基础释义”标签的字体颜色
		noteLabel.setFont(new Font("黑体", Font.PLAIN, 16)); // 设置“基础释义”标签的字体样式
		noteLabel.setBounds(133, 147, 100, 30); // 设置“基础释义”标签的位置与宽高
		backgroundPanel.add(noteLabel); // 把“基础释义”标签添加到背景面板中
		
		wordLabel = new JLabel(); // 创建“单词”标签
		wordLabel.setFont(new Font("黑体", Font.BOLD, 20)); // 设置“单词”标签的字体样式
		wordLabel.setBounds(133, 180, 200, 30); // 设置“单词”标签的位置与宽高
		backgroundPanel.add(wordLabel); // 把“单词”标签添加到背景面板中
		
		explainLabel = new JLabel(); // 创建“词义”标签
		explainLabel.setFont(new Font("宋体", Font.PLAIN, 20)); // 设置“词义”标签的字体样式
		explainLabel.setBounds(133, 220, 300, 30); // 设置“词义”标签的位置与宽高
		backgroundPanel.add(explainLabel); // 把“词义”标签添加到背景面板中
		
		dateLabel = new JLabel(); // 创建“日期”标签
		dateLabel.setForeground(new Color(51, 153, 204)); // 设置“日期”标签的字体颜色
		dateLabel.setFont(new Font("黑体", Font.BOLD, 18)); // 设置“日期”标签的字体样式
		Date date = new Date(); // 创建Date类对象
		String strDate = date + ""; // 把Date类对象转换为String类型
		int spaceFirst = strDate.indexOf(" "); // 获得第一个空格的索引
		int spaceSecond = strDate.indexOf(" ", spaceFirst + 1); // 获得第二个空格的索引
		int spaceThird = strDate.indexOf(" ", spaceSecond + 1); // 获得第三个空格的索引
		String target = strDate.substring(spaceFirst + 1, spaceThird); // 通过截取字符串，获得月份和日期
		dateLabel.setText(target); // 把“日期”标签的文本内容设置为已获得的月份和日期
		dateLabel.setBounds(133, 260, 300, 30); // 设置“日期”标签的位置与宽高
		backgroundPanel.add(dateLabel); // 把“日期”标签添加到背景面板中
		
		String day = strDate.substring(spaceSecond + 1, spaceThird); // 通过截取字符串，获得日期
		Sentence sentence = new Sentence(); // 创建语句类对象
		String strSentence = sentence.show(day); // 根据已获得的日期，获取“英文句子”和“中文译文”
		String[] str = strSentence.split("#"); // 使用"#"拆分已获得的“英文句子”和“中文译文”
		
		sentenceLabel = new JLabel(); // 创建“英文句子”标签
		sentenceLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14)); // 设置“英文句子”标签的字体样式
		sentenceLabel.setText("<html>" + str[0] + "</html>"); // “英文句子”标签显示“英文句子”
		sentenceLabel.setBounds(133, 295, 504, 40); // 设置“英文句子”标签的位置与宽高
		backgroundPanel.add(sentenceLabel); // 把“英文句子”标签添加到背景面板中
		
		lblTranslation = new JLabel(); // 创建“中文译文”标签
		lblTranslation.setFont(new Font("宋体", Font.PLAIN, 14)); // 设置“中文译文”标签的字体样式
		lblTranslation.setText("<html>" + str[1] + "</html>"); // “中文译文”标签显示“中文译文”
		lblTranslation.setBounds(133, 340, 504, 40); // 设置“中文译文”标签的位置与宽高
		backgroundPanel.add(lblTranslation); // 把“中文译文”标签添加到背景面板中
	}

	protected void do_searchButton_mouseEntered(MouseEvent e) { // 鼠标移入“搜索”按钮时
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/pic/btn1.jpg"))); // 设置“搜索”按钮的图标
	}
	
	protected void do_searchButton_mouseExited(MouseEvent e) { // 鼠标移出“搜索”按钮时
		searchButton.setIcon(new ImageIcon(MainFrame.class.getResource("/pic/btn0.jpg"))); // 设置“搜索”按钮的图标
	}
	
	protected void do_searchButton_actionPerformed(ActionEvent e) { // “搜索”按钮发生动作时
		String word = textField.getText(); // 获得文本框中的文本内容
		if (word.equals("")) { // 如果文本框中的文本内容为""
			JOptionPane.showMessageDialog(null, "请输入要查询的单词", "警告", JOptionPane.WARNING_MESSAGE); // 弹出警告对话框
		} else if (!word.matches("^[A-Za-z]+$")) { // 如果文本框中的文本内容不是英文字母（忽略大小写）
			JOptionPane.showMessageDialog(null, "注意：只能输入英文字符（大小写不限）", "错误", JOptionPane.ERROR_MESSAGE); // 弹出错误对话框
		} else { // 如果文本框中的文本内容是英文字母（忽略大小写）
			readFile(); // 调用读取翻译数据源文件的方法
			translation(); // 调用翻译方法
		}
	}

	protected void do_textField_removeUpdate(DocumentEvent e) { // 文本框中的文本删除时
		noteLabel.setText(""); // 设置“基础释义”标签的文本内容为""
		wordLabel.setText(""); // 设置“单词”标签的文本内容为""
		explainLabel.setText(""); // 设置“词义”标签的文本内容为""
	}
	
	/**
	 * 翻译
	 */
	private void translation() {
		String text = textField.getText().trim(); // 获取文本框中的内容
		String trans = getTrans(text); // 根据文本框中的内容获取翻译内容
		if ("".equalsIgnoreCase(trans) || null == trans) { // 如果翻译结果为空
			explainLabel.setText("本地木有，请尝试联网翻译"); // “词义”标签显示“本地木有，请尝试联网翻译”
		} else { // 如果翻译结果不为空
			noteLabel.setText("基础释义"); // “基础释义”标签显示“基础释义”
			wordLabel.setText(textField.getText().toLowerCase()); // “单词”标签显示单词
			explainLabel.setText(trans); // “词义”标签显示词义
		}
	}

	/**
	 * 根据单词内容获取翻译内容
	 * 
	 * @param english
	 *            - 单词单词
	 * @return 寻找单词单词的词义，找到则返回，未找到则返回单词原内容
	 */
	public String getTrans(String text) {
		for (int i = 0; i < data.length; i++) {// 遍历翻译数据
			if (data[i][0].equalsIgnoreCase(text)) {// 判断有没有相同的单词单词
				return data[i][1];// 返回单词的词义
			}
		}
		return "本地木有，请尝试联网翻译";// 否则返回输入的原文
	}

	/**
	 * 读取翻译数据源文件
	 */
	private void readFile() {
		BufferedReader br = null;// 缓冲字符流对象
		try {
			// 获取本类同目录下的数据源文件
			File dateFile = new File("src/words/source");
			// 按照GBK字符编码读取文件
			br = new BufferedReader(new InputStreamReader(new FileInputStream(dateFile), "GBK"));
			String tmp = ""; // 临时字符串变量
			for (int count = 0; (tmp = br.readLine()) != null; count++) { // 逐行读取文件中的内容
				String[] a = tmp.split("#"); // 按照#符号进行分割
				data[count][0] = a[0]; // 单词
				data[count][1] = a[1]; // 词义
			}
		} catch (FileNotFoundException e) { // 捕获文件没有被找到异常
			e.printStackTrace();
		} catch (IOException e) { // 捕获IO输入/输出异常
			e.printStackTrace();
		}
	}
}
