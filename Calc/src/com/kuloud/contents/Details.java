package com.kuloud.contents;

import java.awt.Toolkit;

public class Details {
	public static final int SCREENWIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int SCREENHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static String WELCOME = "感谢你的使用。";
	public static String INTRO = "        一个简单的数值计算器，使用标准 Java组件GUI组件集 Swing构建图形用户界面应用程序。此程序用常用插值法和积分法求解数值问题。\n        插值法包括拉格朗日插值、牛顿插值、埃尔米特插值、分段低次插值、三次样条插值；\n        积分法包括复合梯形公式、复合辛普森公式、龙贝格公式、自适应积分、高斯-勒让德积分、高斯-切比雪夫积分。";
	public static String ex = null;
}
