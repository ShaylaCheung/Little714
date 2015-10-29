package com.baby.cy.babyfun.Music;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.baby.cy.babyfun.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordMusicActivity extends AppCompatActivity {


    @Bind(R.id.record_music)
    Button record_music;
    @Bind(R.id.finish_record_music)
    Button finish_record_music;

    private MediaRecorder recorder;
    private String music_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chenyu.amr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_music_layout);
        ButterKnife.bind(this);
        Log.d("Tomato", music_path);
    }

    @OnClick({R.id.record_music, R.id.finish_record_music})
    public void record_music(Button button) {
        switch (button.getId()) {
            case R.id.record_music:
                recorder = new MediaRecorder();
                // 设置MediaRecorder的音频源为麦克风
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                // 设置MediaRecorder录制的音频格式
                recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
                // 设置MediaRecorder录制音频的编码为amr.
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                // 设置录制好的音频文件保存路径
                recorder.setOutputFile(music_path);

                try {
                    Log.d("Tomato", "准备录制");
                    recorder.prepare();// 准备录制
                    Log.d("Tomato", "开始录制");
                    recorder.start();// 开始录制
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.finish_record_music:
                // 停止刻录
                Log.d("Tomato", "停止刻录");
                recorder.stop();
                // 刻录完成一定要释放资源
                Log.d("Tomato", "释放资源");
                recorder.release();

                break;
        }
    }
}
