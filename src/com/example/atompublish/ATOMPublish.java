package com.example.atompublish;

import com.sun.syndication.feed.atom.Content;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.io.WireFeedInput;
import com.sun.syndication.io.WireFeedOutput;

import javax.swing.text.AbstractDocument;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: davidq
 * Date: 26/07/2013
 * Time: 20:37
 * ATOM test - publication
 */
public class ATOMPublish {
    public static void main(String[] args) {

        // Create feed
        Feed myFeed = loadExistingFeed("/Users/davidq/Temp/atomfeed.atom");
        //Feed myFeed = createNewFeed();

        for (int i=1; i < 5; i++) {
            addEntryToFeed(myFeed,createEntry());
        }

        System.out.println(myFeed.getTitle());
        System.out.println(myFeed.getEntries().toString());

        publishAtomFeed(myFeed);

    }

    static Feed createNewFeed() {
        Feed feed = new Feed();
        try {
            feed.setFeedType("atom_1.0");

            feed.setTitle("Dave's Atom Feed");
            feed.setId("http://www.test.com/");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String RFC3339DateString = sdf.format(new Date());
            Date udate = (Date) sdf.parseObject(RFC3339DateString);

            feed.setUpdated(udate);
        } catch (Exception ee) {
            System.out.println(ee);
        }
        return feed;
    }

    static Entry createEntry() {

        Entry entry = new Entry();
        try {
            Content c = new Content();
            c.setValue("blah blah blah");
            entry.setTitle("entry no 1");
            entry.setId("http://www.compscipleslab.wordpress.com/");
            entry.setSummary(c);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String RFC3339DateString = sdf.format(new Date());
            Date udate = (Date) sdf.parseObject(RFC3339DateString);

            entry.setUpdated(udate);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        return entry;
    }

    static Feed addEntryToFeed(Feed feed, Entry entry) {
        List entries = feed.getEntries();
        if (entries == null) {
            entries = new ArrayList();
        }
        entries.add(entry);
        feed.setEntries(entries);
        return feed;
    }

    static boolean publishAtomFeed(Feed feed) {
        try {
            File RSSDoc = new File("/Users/davidq/Temp/atomfeed.atom");
            if (!RSSDoc.exists()) {
                RSSDoc.createNewFile();
            }
            WireFeedOutput wfo = new WireFeedOutput();
            wfo.output(feed, RSSDoc);
        } catch (Exception ee) {
            System.out.println(ee);
        }
        return true;
    }

    static Feed loadExistingFeed(String url) {
        Feed feed = null;
        try {
            WireFeedInput wfi = new WireFeedInput();
            feed = (Feed) wfi.build(new File(url));
        } catch (Exception ee) {
            System.out.println(ee);
        }
        return feed;
    }

}
