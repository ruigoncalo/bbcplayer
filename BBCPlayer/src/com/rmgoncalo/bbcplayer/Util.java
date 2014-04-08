/*
 * Util methods
 */

package com.rmgoncalo.bbcplayer;


import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Util {

	public void sortList(List<Entry> entries) {
		Collections.sort(entries, new TitlesComparator());
	}

	public class TitlesComparator implements Comparator<Entry> {

		@Override
		public int compare(Entry a, Entry b) {
			return a.getTitle().compareTo(b.getTitle());
		}
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
}
