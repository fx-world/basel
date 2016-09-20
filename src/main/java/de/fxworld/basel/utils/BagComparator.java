package de.fxworld.basel.utils;

import java.util.List;

public class BagComparator {

	public static <T,V> boolean equals(List<T> a, List<V> b) {
		boolean result = true;
		
		if ((a != null) && (b != null)) {
			result = a.size() == b.size();
			
			if (result) {
				for (int i = 0; (i < a.size()) && result; i++) {
					result &= a.equals(b);					
				}
			}			
			
		} else if ((a != null) && (b == null)) {
			result = false;
			
		} else if ((a == null) && (b != null)) {
			result = false;
		}
		
		return result;
	}
}
