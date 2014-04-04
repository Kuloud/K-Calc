package com.kuloud.main;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.kuloud.algorithms.Interpolation;
import com.kuloud.contents.Def;
import com.kuloud.contents.ErrMessage;
import com.kuloud.contents.LabelText;
import com.kuloud.contents.Prompt;

public class SpPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton sp1RadioButton;
	private JRadioButton sp2RadioButton;
	private JRadioButton sp3RadioButton;
	private JButton spCalcButton;
	private JButton spClearButton;
	private JTextField spDY11TextField;
	private JTextField spDY12TextField;
	private JLabel spDY12Label;
	private JLabel spDY11Label;
	private JPanel spDY1Panel;
	private JTextField spDY21TextField;
	private JTextField spDY22TextField;
	private JLabel spDY22Label;
	private JLabel spDY21Label;
	private JPanel spDY2Panel;
	private JLabel spDY3Label;
	private JPanel spDY3Panel;
	private JPanel spDYPanel;
	private CardLayout cl2;
	private JLabel spResultLabel;
	private JLabel spTLabel;
	private JLabel spYLabel;
	private JLabel spXLabel;
	private ButtonGroup bg2;
	private JPanel spPanel;
	private JTextField spResultTextField;
	private JTextField spTTextField;
	private JTextField spXTextField;
	private JTextField spYTextField;

	public SpPanel() {
		super();
		initialize();
	}

	protected void calcSpValue() {
		double t = 0.0;
		double result = 0.0;
		int i, j, k;
		double[] x = new double[Def.N];
		double[] y = new double[Def.N];

		try {
			t = Double.parseDouble(spTTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, ErrMessage.T_WRONG);
			spTTextField.requestFocus();
			spTTextField.selectAll();
		}

		String xstr = spXTextField.getText();
		String ystr = spYTextField.getText();
		Scanner xs = new Scanner(xstr);
		Scanner ys = new Scanner(ystr);

		k = 0;
		while (ys.hasNextDouble()) {
			y[k] = ys.nextDouble();
			k++;
		}
		j = k;

		k = 0;
		while (xs.hasNextDouble()) {
			x[k] = xs.nextDouble();
			k++;
		}
		i = k;

		// 若x个数不为2，则可知输入数据一定为不等距的
		if (i != 2) {
			if (i != j) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, ErrMessage.NOT_MATCH);
				spXTextField.requestFocus();
				spXTextField.selectAll();
			} else {
				// x与y的个数匹配，检查x是否为升序输入
				for (k = 0; k < i - 2; k++) {
					if (x[k] > x[k + 1]) {
						JOptionPane.showMessageDialog(null, "请按升序输入x值。");
						spXTextField.requestFocus();
						spXTextField.selectAll();
					}
				}
				result = clacSp(j, x, y, t);
			}
		} else {
			if (j < 2) {
				// x与y的个数不匹配
				JOptionPane.showMessageDialog(null, "输入格式错误，请检查重输。");
				spXTextField.requestFocus();
				spXTextField.selectAll();
			} else {
				result = clacSp(j, x[0], x[1] - x[0], y, t);
			}
		}

		spResultTextField.setText(result + "");
	}

	/**
	 * 等距三次样条插值
	 * 
	 * @param n
	 *            节点个数
	 * @param x
	 *            第一个插值节点
	 * @param xStep
	 *            步长
	 * @param y
	 *            插值节点对应的函数值
	 * @param t
	 *            自变量
	 * @return 所求插值节点函数值
	 */
	private double clacSp(int n, double x, double xStep, double[] y, double t) {
		double f1 = 0.0, f2 = 0.0;
		double result = 0.0;
		if (sp1RadioButton.isSelected()) {
			try {
				f1 = Double.parseDouble(spDY11TextField.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "S'(x0)输入错误，请检查并修改。");
				spDY11TextField.requestFocus();
				spDY11TextField.selectAll();
			}
			try {
				f2 = Double.parseDouble(spDY12TextField.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "S'(xn)输入错误，请检查并修改。");
				spDY12TextField.requestFocus();
				spDY12TextField.selectAll();
			}

			result = Interpolation.getValueSpline1(n, x, xStep, y, f1, f2, t);
		} else if (sp2RadioButton.isSelected()) {
			try {
				f1 = Double.parseDouble(spDY21TextField.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "S\"(x0)输入错误，请检查并修改。");
				spDY21TextField.requestFocus();
				spDY21TextField.selectAll();
			}
			try {
				f2 = Double.parseDouble(spDY22TextField.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "S\"(xn)输入错误，请检查并修改。");
				spDY22TextField.requestFocus();
				spDY22TextField.selectAll();
			}
			result = Interpolation.getValueSpline2(n, x, xStep, y, f1, f2, t);
		} else {
			result = Interpolation.getValueSpline3(n, x, xStep, y, t);
		}
		return result;
	}

	/**
	 * 等距三次样条插值
	 * 
	 * @param n
	 *            节点个数
	 * @param x
	 *            插值节点
	 * @param y
	 *            插值节点对应的函数值
	 * @param t
	 *            自变量
	 * @return 所求插值节点函数值
	 */
	private double clacSp(int n, double[] x, double[] y, double t) {
		double f1 = 0.0, f2 = 0.0;
		double result = 0.0;
		if (sp1RadioButton.isSelected()) {
			try {
				f1 = Double.parseDouble(spDY11TextField.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "S'(x0)输入错误，请检查并修改。");
				spDY11TextField.requestFocus();
				spDY11TextField.selectAll();
			}
			try {
				f2 = Double.parseDouble(spDY12TextField.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "S'(xn)输入错误，请检查并修改。");
				spDY12TextField.requestFocus();
				spDY12TextField.selectAll();
			}

			result = Interpolation.getValueSpline1(n, x, y, f1, f2, t);
		} else if (sp2RadioButton.isSelected()) {
			try {
				f1 = Double.parseDouble(spDY21TextField.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "S\"(x0)输入错误，请检查并修改。");
				spDY21TextField.requestFocus();
				spDY21TextField.selectAll();
			}
			try {
				f2 = Double.parseDouble(spDY22TextField.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "S\"(xn)输入错误，请检查并修改。");
				spDY22TextField.requestFocus();
				spDY22TextField.selectAll();
			}
			result = Interpolation.getValueSpline2(n, x, y, f1, f2, t);
		} else {
			result = Interpolation.getValueSpline3(n, x, y, t);
		}
		return result;
	}

	protected void clearSp() {
		spXTextField.setText("");
		spYTextField.setText("");
		spTTextField.setText("");
		spDY11TextField.setText("");
		spDY12TextField.setText("");
		spDY21TextField.setText("");
		spDY22TextField.setText("");
		spResultTextField.setText("");
	}

	private JPanel getSpDY1Panel() {
		if (spDY1Panel == null) {
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints4.weightx = 1.0;
			spDY12Label = new JLabel();
			spDY12Label.setText("S'(xn)=");
			spDY12Label.setBounds(new Rectangle(235, 5, 40, 20));
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.weightx = 1.0;
			spDY11Label = new JLabel();
			spDY11Label.setText("S'(x0)=");
			spDY11Label.setBounds(new Rectangle(30, 5, 40, 20));
			spDY1Panel = new JPanel();
			spDY1Panel.setLayout(null);
			spDY1Panel.setName("spDY1Panel");
			spDY1Panel.add(spDY11Label, new GridBagConstraints());
			spDY1Panel.add(spDY11TextField, gridBagConstraints3);
			spDY1Panel.add(spDY12Label, new GridBagConstraints());
			spDY1Panel.add(spDY12TextField, gridBagConstraints4);
		}
		return spDY1Panel;
	}

	private JPanel getSpDY2Panel() {
		if (spDY2Panel == null) {
			spDY22Label = new JLabel();
			spDY22Label.setBounds(new Rectangle(235, 5, 50, 20));
			spDY22Label.setText("S\"(xn)=");
			spDY21Label = new JLabel();
			spDY21Label.setBounds(new Rectangle(30, 5, 45, 20));
			spDY21Label.setText("S\"(x0)=");
			spDY2Panel = new JPanel();
			spDY2Panel.setLayout(null);
			spDY2Panel.setName("spDY2Panel");
			spDY2Panel.add(spDY21Label, null);
			spDY2Panel.add(spDY21TextField, null);
			spDY2Panel.add(spDY22Label, null);
			spDY2Panel.add(spDY22TextField, null);
		}
		return spDY2Panel;
	}

	private JPanel getSpDY3Panel() {
		if (spDY3Panel == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.gridy = 0;
			spDY3Label = new JLabel();
			spDY3Label
					.setText("S(x0+0)=S(xn-0)    S'(x0+0)=S'(xn-0)    S''(x0+0)=S''(xn-0)");
			spDY3Panel = new JPanel();
			spDY3Panel.setLayout(new GridBagLayout());
			spDY3Panel.setName("spDY3Panel");
			spDY3Panel.add(spDY3Label, gridBagConstraints5);
		}
		return spDY3Panel;
	}

	private JPanel getSpDYPanel() {
		if (spDYPanel == null) {
			spDYPanel = new JPanel();
			cl2 = new CardLayout();
			spDYPanel.setLayout(cl2);
			spDYPanel.setBounds(new Rectangle(10, 120, 410, 30));
			spDYPanel.add(getSpDY1Panel(), getSpDY1Panel().getName());
			spDYPanel.add(getSpDY2Panel(), getSpDY2Panel().getName());
			spDYPanel.add(getSpDY3Panel(), getSpDY3Panel().getName());
		}
		return spDYPanel;
	}

	private JPanel getSpPanel() {
		if (spPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			spPanel = new JPanel();
			spPanel.setLayout(new GridBagLayout());
			spPanel.setBounds(new Rectangle(10, 90, 410, 30));
			spPanel.add(sp1RadioButton, gridBagConstraints);
			spPanel.add(sp2RadioButton, gridBagConstraints1);
			spPanel.add(sp3RadioButton, gridBagConstraints2);
			bg2 = new ButtonGroup();
			bg2.add(sp1RadioButton);
			bg2.add(sp2RadioButton);
			bg2.add(sp3RadioButton);
		}
		return spPanel;
	}

	private void initialize() {
		this.setSize(430, 210);
		this.setLayout(null);

		spResultLabel = new JLabel();
		spResultLabel.setBounds(new Rectangle(10, 180, 100, 20));
		spResultLabel.setText(LabelText.RESULTTEXT);
		spTLabel = new JLabel();
		spTLabel.setBounds(new Rectangle(10, 70, 100, 20));
		spTLabel.setText(LabelText.TTEXT);
		spYLabel = new JLabel();
		spYLabel.setBounds(new Rectangle(10, 40, 100, 20));
		spYLabel.setText(LabelText.YTEXT);
		spXLabel = new JLabel();
		spXLabel.setBounds(new Rectangle(10, 10, 100, 20));
		spXLabel.setText(LabelText.XTEXT);

		spResultTextField = new JTextField();
		spResultTextField.setBounds(new Rectangle(120, 179, 150, 22));
		spResultTextField.setEditable(false);
		spYTextField = new JTextField();
		spYTextField.setBounds(new Rectangle(120, 39, 300, 22));
		spYTextField.setToolTipText(Prompt.Y_TIP);
		spXTextField = new JTextField();
		spXTextField.setBounds(new Rectangle(120, 9, 300, 22));
		spXTextField.setToolTipText(Prompt.X_TIP);
		spTTextField = new JTextField();
		spTTextField.setBounds(new Rectangle(120, 69, 150, 22));
		spTTextField.setToolTipText(Prompt.T_TIP);
		spDY22TextField = new JTextField();
		spDY22TextField.setBounds(new Rectangle(290, 4, 100, 22));
		spDY22TextField.setToolTipText(Prompt.DY_TIP);
		spDY21TextField = new JTextField();
		spDY21TextField.setBounds(new Rectangle(80, 4, 100, 22));
		spDY21TextField.setToolTipText(Prompt.DY_TIP);
		spDY12TextField = new JTextField();
		spDY12TextField.setBounds(new Rectangle(290, 4, 100, 22));
		spDY12TextField.setToolTipText(Prompt.DY_TIP);
		spDY11TextField = new JTextField();
		spDY11TextField.setBounds(new Rectangle(75, 4, 100, 22));
		spDY11TextField.setToolTipText(Prompt.DY_TIP);

		sp1RadioButton = new JRadioButton();
		sp1RadioButton.setText("第一种边界条件");
		sp1RadioButton.setSelected(true);
		sp1RadioButton.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				cl2.show(spDYPanel, "spDY1Panel");
			}
		});
		sp2RadioButton = new JRadioButton();
		sp2RadioButton.setText("第二种边界条件");
		sp2RadioButton.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				cl2.show(spDYPanel, "spDY2Panel");
			}
		});
		sp3RadioButton = new JRadioButton();
		sp3RadioButton.setText("第三种边界条件");
		sp3RadioButton.addItemListener(new java.awt.event.ItemListener() {
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e) {
				cl2.show(spDYPanel, "spDY3Panel");
			}
		});

		spCalcButton = new JButton();
		spCalcButton.setBounds(new Rectangle(100, 150, 60, 20));
		spCalcButton.setText(LabelText.CALC);
		spCalcButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				calcSpValue();
			}
		});
		spClearButton = new JButton();
		spClearButton.setBounds(new Rectangle(250, 150, 60, 20));
		spClearButton.setText(LabelText.CLEAR);
		spClearButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				clearSp();
			}
		});

		this.add(spXLabel, null);
		this.add(spXTextField, null);
		this.add(spYLabel, null);
		this.add(spYTextField, null);
		this.add(spTLabel, null);
		this.add(spTTextField, null);
		this.add(getSpPanel(), null);
		this.add(spCalcButton, null);
		this.add(spClearButton, null);
		this.add(spResultLabel, null);
		this.add(spResultTextField, null);
		this.add(getSpDYPanel(), null);
	}
}
