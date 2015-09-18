package com.yicai.autonews.calculation;

import java.util.Comparator;

import com.github.cyl.autonews.pojo.indicator.Indicator;

public class IndicatorYoyComparator implements Comparator<Indicator> {

	@Override
	public int compare(Indicator o1, Indicator o2) {
		double yoy1 = o1.getYoy();
		double yoy2 = o2.getYoy();
		return Double.compare(yoy1, yoy2);
	}

}
