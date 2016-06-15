package com.yhjia.me.util.sort;

import java.util.Comparator;

/**
 * 
 * @author JiangHao
 *
 */
public class SortComparator implements Comparator<ISort> {

	public int compare(ISort o1, ISort o2) {
		if (o1.getSortLetter().equals("@")
				|| o2.getSortLetter().equals("#")) {
			return -1;
		} else if (o1.getSortLetter().equals("#")
				|| o2.getSortLetter().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetter().compareTo(o2.getSortLetter());
		}
	}

}
