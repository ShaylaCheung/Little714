package com.baby.cy.babyfun;
/**
 * Created by chenyu on 15/10/24.
 */
public class MusicInfos {
    private String music_name;
    private String singer_name;

    public MusicInfos(String music_name,String singer_name){
        this.music_name = music_name;
        this.singer_name = singer_name;
    }


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
}
