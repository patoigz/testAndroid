package com.pgdev.reddit.util;

/**
 * Created by patoi on 09-Feb-18.
 */

public class TimeAgo {
    public static String fromNow(long timeStamp) {
        long timeDiffernce;
        long unixTime = System.currentTimeMillis() / 1000L;
        int j;
        String[] periods = {"seconds", "minutes", "hours", "days", "weeks", "months", "years", "d"};
        double[] lengths = {60, 60, 24, 7, 4.35, 12, 10};
        timeDiffernce = unixTime - timeStamp;
        for (j = 0; timeDiffernce >= lengths[j] && j < lengths.length - 1; j++) {
            timeDiffernce /= lengths[j];
        }
        return timeDiffernce + " " + periods[j] + " ago";
    }
}
