package com.example.sbf;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.res.AssetFileDescriptor;
import android.view.View;

public class DacMedia extends Activity {
	private MediaPlayer mediaPlayer;
//	private int playbackPosition=0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			playLocalAudio_UsingDescriptor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		setContentView(R.layout.activity_dac_media);
	}
//	public void doClick(View view) {
//		switch(view.getId()) {
//		case R.id.startPlayerBtn:
//		try {
//			playLocalAudio_UsingDescriptor();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		break;
//		case R.id.pausePlayerBtn:
//		if(mediaPlayer != null && mediaPlayer.isPlaying()) {
//			playbackPosition = mediaPlayer.getCurrentPosition();
//			mediaPlayer.pause();
//		}
//		break;
//		case R.id.restartPlayerBtn:
//			if(mediaPlayer != null && !mediaPlayer.isPlaying()) {
//				mediaPlayer.seekTo(playbackPosition);
//				mediaPlayer.start();
//		}
//		break;
//		case R.id.stopPlayerBtn:
//			if(mediaPlayer != null) {
//				mediaPlayer.stop();
//				playbackPosition = 0;
//		}
//		break;
//		}
//	}
	public void playLocalAudio_UsingDescriptor() throws Exception {
	
		AssetFileDescriptor fileDesc = getResources().openRawResourceFd(
		R.raw.night);
		if (fileDesc != null) {
		
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(fileDesc.getFileDescriptor(), fileDesc
			.getStartOffset(), fileDesc.getLength());
			
			fileDesc.close();
			
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		killMediaPlayer();
	}
	
	private void killMediaPlayer() {
		if(mediaPlayer!=null) {
		try {
			mediaPlayer.release();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		}
	}
}