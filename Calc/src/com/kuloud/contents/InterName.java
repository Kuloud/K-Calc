package com.kuloud.contents;

public enum InterName {
	拉格朗日插值(1),牛顿插值(2),埃尔米特插值(3),分段低次插值(4),三次样条插值(5);

	private final int interName;
	private InterName(int name) {
		interName = name;
	}
	public int getName() {
		return interName;
	}
}