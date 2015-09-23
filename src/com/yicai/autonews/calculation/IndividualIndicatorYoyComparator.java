package com.yicai.autonews.calculation;

import java.util.Comparator;

import com.github.cyl.autonews.pojo.indicator.Indicator;

public class IndividualIndicatorYoyComparator implements Comparator<IndividualIndicator> {

	@Override
	public int compare(IndividualIndicator o1, IndividualIndicator o2) {
		double yoy1 = o1.getYoy();
		double yoy2 = o2.getYoy();
		return Double.compare(yoy1, yoy2);
	}

}
