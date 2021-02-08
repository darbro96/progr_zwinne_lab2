package com.project.rest.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Track {
    private String url;
    private String name;

    public Track(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public Track() {

    }
}
