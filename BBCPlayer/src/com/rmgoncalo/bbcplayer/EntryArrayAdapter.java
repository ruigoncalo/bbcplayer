/*
 * Class used to fill in the listview UI element
 * Each row is filled with the title and id strings
 */

package com.rmgoncalo.bbcplayer;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryArrayAdapter extends ArrayAdapter<Entry>{
	
	//private static final String logtag = "ArrayAdapter";
	private final Context context;
	private final List<Entry> values;
		
	public EntryArrayAdapter(Context context, List<Entry> values){
		super(context, R.layout.entry, values);
		this.context = context;
		this.values = values;
	}
			
	// Use the entry layout to each row
	// Use title and id strings of each Entry object
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
		View rowView = inflater.inflate(R.layout.entry, parent, false);
			
		TextView titleView = (TextView) rowView.findViewById(R.id.title);
		TextView idView = (TextView) rowView.findViewById(R.id.id);
			
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.thumb);		
		titleView.setText(values.get(position).getTitle());
		idView.setText(values.get(position).getId());
			
		return rowView;
	}
}
