package com.kuloud.contents;

public enum IntegName {
	复合梯形公式(1),复合辛普森公式(2),龙贝格积分(3),自适应积分(4),高斯勒让德积分(5);
	
	private final int integName;
	private IntegName(int name) {
		integName = name;
	}
	public int getName() {
		return integName;
	}
	public static String[] toStrArray() {
		IntegName[] temp = IntegName.values();
		String[] str = new String[temp.length];
		for (int i=0; i<temp.length;i++) {
			str[i] = temp[i].name();
		}
		return str;
	}
}
