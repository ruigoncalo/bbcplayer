/*
 * Class used to create an xml parser (XmlPullParser)
 * Parser will read the bbc page and create a list of entries
 */

package com.rmgoncalo.bbcplayer;

import java.io.IOException;
import java.io.InputStream;
//import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;
import android.util.Xml;

public class XmlParser {
	
private static final String logtag = "XmlParser";
	
	private static final String TITLE_TAG = "title";
	private static final String ID_TAG = "id";
	private static final String UPDATED_TAG = "updated";
	private static final String CONTENT_TAG = "content";
	
	private static final String ns = null;
	   
    public List<Entry> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
    
    private List<Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List<Entry> entries = new ArrayList<Entry>();

        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                entries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }  
        
        return entries;
    }
      
    private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");
        
        Entry entry = new Entry();
        
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TITLE_TAG)) {
                entry.setTitle(readTag(TITLE_TAG, parser));
            } else if (name.equals(ID_TAG)) {
                entry.setId(readTag(ID_TAG, parser));
            } else if (name.equals(UPDATED_TAG)) {
                entry.setUpdated(readTag(UPDATED_TAG, parser));
            } else if (name.equals(CONTENT_TAG)) {
            	entry.setContent(readTag(CONTENT_TAG, parser));
            } else {
                skip(parser);
            }
        }
        
        //TODO: parse the content text and extract the img src text
        //entry.setThumb(parseThumbLink(entry.getContent()));
        
        //Log.d(logtag, "entry thumb: " + parseThumbLink(entry.getContent()));
        return entry;
    }

    //General method to process the tag's content in the feed
    private String readTag(String tag, XmlPullParser parser) throws IOException, XmlPullParserException {
    	parser.require(XmlPullParser.START_TAG, ns, tag);
    	String s = readText(parser);
    	parser.require(XmlPullParser.END_TAG, ns, tag);
    	
    	Log.d(logtag, "readTag: " + s );
    	
        return s;
    }

    // Extracts text values from the tags.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
    
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
            case XmlPullParser.END_TAG:
                depth--;
                break;
            case XmlPullParser.START_TAG:
                depth++;
                break;
            }
        }
     }
    
    /*
    private String parseThumbLink(String str) throws XmlPullParserException, IOException {
    	
    	String result = "";
    	
    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        
        // receive the content string
        parser.setInput(new StringReader(str));
        
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
         if(eventType == XmlPullParser.START_DOCUMENT) {
            Log.d(logtag, "start document");
         } else if(eventType == XmlPullParser.START_TAG) {
             Log.d(logtag, "start tag");
         } else if(eventType == XmlPullParser.END_TAG) {
             Log.d(logtag, "end tag: " + parser.getName());
         } else if(eventType == XmlPullParser.TEXT) {
        	 result = parser.getText();
             Log.d(logtag, "text: " + parser.getText());
         }
         eventType = parser.next();
        }   	
    	
    	return result;
    }
    */
}
