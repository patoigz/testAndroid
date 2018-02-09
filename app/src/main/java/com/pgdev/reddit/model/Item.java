package com.pgdev.reddit.model;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by patoi on 08-Feb-18.
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class Item implements Serializable {

    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private long created_utc;
    @NonNull
    private String thumbnail;
    @NonNull
    private long num_comments;

    private boolean read = false;
}
