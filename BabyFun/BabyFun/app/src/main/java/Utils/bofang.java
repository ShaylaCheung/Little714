//在Manifest里的申明
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WAKE_LOCK" />

//在对应的java文件里
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;

public class AudioRecordAndPlay extends Activity
{
    private static String mFileName = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private PauseButton  mPlayButton2 = null;
    private StopButton   mPlayButton3 = null;
    private MediaPlayer   mPlayer = null;

    private int playSelectedRow;        //用于记录表格中选定表格的行号
    private int recordAmount;

    private boolean playing = false;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {

			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        }
        catch(IllegalArgumentException e1) {
			Log.e(LOG_TAG, "The file is not exit.");
		}
        catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        } finally {
        	playing = true;
        }
    }

    private void pausePlaying() {
    	mPlayer.pause();
    }

    private void rePlaying() {
    	mPlayer.start();
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
        playing = false;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
    	recordAmount += 1;
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
    	boolean mStartPlaying;
    	
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
            	//歌曲还在播放
    			if(this.playing == true){
    				stopPlaying();
    				playing = false;
    				mStartPlaying = true;
    			} else{
  					mStartPlaying = true;
    			}

    			//得到选中的行号，并加1，即要播放歌曲的编号，具体实现方法要根据使用的组件而定
    			//具体如何获得所选行的歌曲的路径待完善
				playSelectedRemoveRow = list.getSelectedRow()+1;

                
                onPlay(mStartPlaying);
                // if (mStartPlaying) {
                //     setText("Stop playing");
                // } else {
                //     setText("Start playing");
                // }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    class PauseButton extends Button {
    	boolean mPausePlaying = false;
    	
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
            	//歌曲还在播放
    			if(this.playing == true && mPausePlaying == false){
    				pausePlaying();
    				mPausePlaying = true;
    			}
    			if(this.playing == true && mPausePlaying == true){
    				rePlaying();
    				mPausePlaying = false;
    			}
            }
        };

        public PauseButton(Context ctx) {
            super(ctx);
            setText("Pause playing");
            setOnClickListener(clicker);
        }
    }

    class StopButton extends Button {
    	boolean mStartPlaying;
    	
        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
            	//歌曲还在播放
    			if(this.playing == true){
    				playing = false;
    				mStartPlaying = false;
    			}

                onPlay(mStartPlaying);
                mStartPlaying = !mStartPlaying;
            }
        };

        public StopButton(Context ctx) {
            super(ctx);
            setText("Stop playing");
            setOnClickListener(clicker);
        }
    }

    public AudioRecordAndPlay() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/record"+recordAmount+".3gp";
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        LinearLayout ll = new LinearLayout(this);
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton2 = new PauseButton(this);
        ll.addView(mPlayButton2,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton3 = new StopButton(this);
        ll.addView(mPlayButton3,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        setContentView(ll);

        mPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}