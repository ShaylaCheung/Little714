package com.baby.cy.babyfun.Music;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baby.cy.babyfun.R;

import java.io.IOException;

import Utils.URLUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayMusicActivity extends Activity{

    @Bind(R.id.play_music) Button play_music;
    @Bind(R.id.pause_music) Button pause_music;
    @Bind(R.id.play_music_name) TextView play_music_name;
    private static String mFileName = null;

    private MediaPlayer mPlayer = null;

    private boolean isPlaying = false;    //播放状态
    private boolean isPause = false;
    private Intent intent;

    private static final int MOTHER_INTENT = 2;
    private static final int FIND_INTENT = 1;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.play_music_layout);
        ButterKnife.bind(this);
        intent = getIntent();
        int whichIntent = intent.getIntExtra("whichIntent",0);
        if(whichIntent==FIND_INTENT){
            play_find_music();
        }else if(whichIntent == MOTHER_INTENT){
            play_mother_music();
        }
    }

    public void play_find_music(){
        String music_path = intent.getStringExtra("music_path");
        String music_name = intent.getStringExtra("music_name");
        mFileName = URLUtils.play_find_music_url+music_path;
        play_music_name.setText(music_name);
        Log.d("Tomato", mFileName);
    }

    public void play_mother_music(){
        String music_name = intent.getStringExtra("music_name");
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath()+"/music/"+music_name+".amr";
        play_music_name.setText(music_name);
        Log.d("Tomato", mFileName);
    }
    /**
     * 开始播放
     */
    private void startPlaying() {

        mPlayer = new MediaPlayer();
        try {

            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        }
        catch(IllegalArgumentException e1) {
            Log.e("Tomato", "The file is not exit.");
        }
        catch (IOException e) {
            Log.e("Tomato", "prepare() failed");
        } finally {
            isPlaying = true;
        }
    }

    /**
     * 暂停播放
     */
    private void pausePlaying() {
        if(mPlayer!=null){
            mPlayer.pause();
            isPause = true;
        }

    }

    /**
     * 重新播放
     */
    private void replaying(){
        if(mPlayer!=null){
            mPlayer.start();
        }

    }

    /**
     * 停止播放
     */
    private void stopPlaying() {
        if(mPlayer!=null){
            mPlayer.stop();
        }
    }


    @OnClick(R.id.play_music)
    public void play_music(){

        if(mPlayer!=null){
            if(isPause){
                replaying();
                isPause = false;
            }else{
                stopPlaying();
                startPlaying();
            }
        }else{
            startPlaying();
        }

        play_music.setVisibility(View.INVISIBLE);
        pause_music.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.pause_music)
    public void pause_music(){
        pausePlaying();
        pause_music.setVisibility(View.INVISIBLE);
        play_music.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
