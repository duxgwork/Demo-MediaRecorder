package con.me.kevindue.recorder;

import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;
import con.me.kevindue.recorder.listener.OnPlayCompletionListener;

/**
 * 音频播放器
 */
public class RecorderPlayer {
    private MediaPlayer mediaPlayer;

    private RecorderPlayer() {

    }
    private static class SingletonHolder {

        private static final RecorderPlayer INSTANCE = new RecorderPlayer();

    }

    public static RecorderPlayer getInstance() {
        return RecorderPlayer.SingletonHolder.INSTANCE;
    }

    public void startPlay(String filePath, final OnPlayCompletionListener listener) {
        if (TextUtils.isEmpty(filePath)){
            throw new IllegalArgumentException("MediaPlayer file is empty !");
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (listener != null){
                        listener.onCompletion(mediaPlayer);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void  pausePlay() {
        if (mediaPlayer != null){
            mediaPlayer.pause();
        }
    }

    public void  resumePlay() {
        if (mediaPlayer != null){
            mediaPlayer.start();
        }
    }

    public void stopPlay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public void releasePlay() {
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPalying() {
        if (mediaPlayer != null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }

}
