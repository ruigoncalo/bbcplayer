/*
 * Class used to parse json data
 */

package com.rmgoncalo.bbcplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public class JsonParser {

	private static final String logtag = "JsonParser";

	private static final String TAG_TOTAL = "total";
	private static final String TAG_MOVIES = "movies";
	private static final String TAG_RATINGS = "ratings";
	private static final String TAG_SCORE = "critics_score";

	private String urlString;
	private Integer score;

	public String getUrl() {
		return urlString;
	}

	public void setUrl(String url) {
		this.urlString = url;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	private InputStream downloadUrl() throws IOException {
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

	private int readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readTotal(reader);
		} finally {
			reader.close();
		}
	}

	private int readTotal(JsonReader reader) throws IOException {
		int total = 0;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_TOTAL)) {
				total = reader.nextInt();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

		if (total > 0)
			return readScore(reader, TAG_MOVIES);
		else
			return 0;
	}

	private int readScore(JsonReader reader, String tag) throws IOException {

		int score = 0;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(tag)) {
				score = readRatings(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

		return score;
	}

	private int readRatings(JsonReader reader) throws IOException {
		
		int ratings = 0;
		
		reader.beginArray();
		if (reader.hasNext()){
			reader.beginObject();
			 while (reader.hasNext()) {
				 String name = reader.nextName();
				 if (name.equals(TAG_RATINGS)) {
					 ratings = readScore(reader, TAG_SCORE);
				 } else {
				 reader.skipValue();
				 }
			 }		 
			reader.endObject();
		}
		reader.endArray();
		
		return ratings;
	}	
	

	// Implementation of AsyncTask used to download JSON file
	private class DownloadJsonTask extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void...params) {
			try {
				loadJsonFromNetwork();
			} catch (IOException e) {
				Log.d(logtag, "doInBackground IOException");
				return null;
			}
			
			return null;
		}

	}

	private void loadJsonFromNetwork() throws IOException {
		InputStream stream = null;
		Integer score = Integer.valueOf(0);

		try {
			stream = downloadUrl();
			score = readJsonStream(stream);

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {
			if (stream != null) {
				stream.close();
			}
		}

		this.score = score;
	}
	
	public void start(){
		DownloadJsonTask jtask = new DownloadJsonTask();
		jtask.execute();
	}

}
