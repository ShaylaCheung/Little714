package com.baby.cy.babyfun.Bean;
/**
 * Created by chenyu on 15/10/24.
 */
public class MusicInfos {
    private Long id;
    private String music_name;
    private String singer_name;
    private String music_path;


    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getSinger_name() {
        return singer_name;
    }

    public void setSinger_name(String singer_name) {
        this.singer_name = singer_name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMusic_path() {
        return music_path;
    }

    public void setMusic_path(String music_path) {
        this.music_path = music_path;
    }
}
