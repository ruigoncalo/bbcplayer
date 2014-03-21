/*
 * Class used to sort the arraylist alphabetically
 */

package com.rmgoncalo.bbcplayer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Util {

	public void sortList(List<Entry> entries){
		Collections.sort(entries, new TitlesComparator());
	}
	
	public class TitlesComparator implements Comparator<Entry>{
		
		@Override
		public int compare(Entry a, Entry b){
			return a.getTitle().compareTo(b.getTitle());
		}
	}
}
