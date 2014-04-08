package com.rmgoncalo.bbcplayer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	private static final String URL = "http://feeds.bbc.co.uk/iplayer/categories/films/tv/list";
	private static final String logtag = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new DownloadXmlTask().execute(URL);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Implementation of AsyncTask used to download XML feed
	private class DownloadXmlTask extends AsyncTask<String, Void, List<Entry>> {
		@Override
		protected List<Entry> doInBackground(String... urls) {
			try {
				return loadXmlFromNetwork(urls[0]);
			} catch (IOException e) {
				Log.d(logtag, "doInBackground IOException");
				return null;
			} catch (XmlPullParserException e) {
				Log.d(logtag, "doInBackground XmlPullParserException");
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<Entry> entries) {
			setContentView(R.layout.activity_main);
			final ListView lv = (ListView) findViewById(R.id.list);
			
			// TODO: make sortList static
			Util u = new Util();
			u.sortList(entries);
			
			

			lv.setAdapter(new EntryArrayAdapter(getApplicationContext(),
					entries));
		}
	}

	// Uploads XML and parses it

	private List<Entry> loadXmlFromNetwork(String urlString)
			throws XmlPullParserException, IOException {
		InputStream stream = null;
		// Instantiate the parser
		XmlParser xmlParser = new XmlParser();
		List<Entry> entries = null;

		try {
			stream = downloadUrl(urlString);
			entries = xmlParser.parse(stream);

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (stream != null) {
				stream.close();
			}
		}

		return entries;
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		return conn.getInputStream();
	}

}
