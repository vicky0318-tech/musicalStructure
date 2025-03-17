package com.example.songify; 
 
import android.media.MediaPlayer; 
import android.os.Bundle; 
import android.os.Handler; 
import android.widget.Button; 
import android.widget.SeekBar; 
import android.widget.TextView; 
 
import androidx.appcompat.app.AppCompatActivity; 
 
public class MainActivity extends AppCompatActivity { 
 
    private Button playButton, prevButton, nextButton; 
    private SeekBar seekBar; 
    private TextView textView; 
    private MediaPlayer mediaPlayer; 
    private int currentSongIndex = 0; 
    private Handler handler = new Handler(); 
    private boolean isSeekBarUpdating = true; 
 
    // Array of song resources (Place MP3 files in res/raw folder) 
    private int[] songs = {R.raw.animal, R.raw.faded, R.raw.maroon}; 
    private String[] songTitles = {"Animal", "Faded", "Maroon"};  // Song 
names 
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_main); 
 
        // Initialize UI elements 
        playButton = findViewById(R.id.img_1); 
        prevButton = findViewById(R.id.prev); 
        nextButton = findViewById(R.id.samp);  // Updated to 'next' button 
ID 
        seekBar = findViewById(R.id.seekBar); 
        textView = findViewById(R.id.textView); 
 
        // Initialize MediaPlayer with the first song 
        initializeMediaPlayer(); 
 
        // Play Button Click Listener 
        playButton.setOnClickListener(v -> togglePlayPause()); 
 
        // Next Button Click Listener 
        nextButton.setOnClickListener(v -> playNextSong()); 
 
        // Previous Button Click Listener 
        prevButton.setOnClickListener(v -> playPreviousSong()); 
 
        // Handle SeekBar changes 
        seekBar.setOnSeekBarChangeListener(new 
SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onProgressChanged(SeekBar seekBar, int progress, 
boolean fromUser) { 
                if (fromUser && mediaPlayer != null) { 
                    mediaPlayer.seekTo(progress); 
                } 
            } 
 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {} 
 
            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {} 
        }); 
 
        // Handle song completion 
        mediaPlayer.setOnCompletionListener(mp -> playNextSong()); 
 
        // Start updating SeekBar 
        updateSeekBar(); 
    } 
 
    private void initializeMediaPlayer() { 
        if (mediaPlayer != null) { 
            mediaPlayer.release(); 
        } 
        mediaPlayer = MediaPlayer.create(this, songs[currentSongIndex]); 
        textView.setText(songTitles[currentSongIndex]); // Display song 
name 
        seekBar.setMax(mediaPlayer.getDuration()); 
    } 
 
    private void togglePlayPause() { 
        if (mediaPlayer == null) return; 
 
        if (mediaPlayer.isPlaying()) { 
            mediaPlayer.pause(); 
            playButton.setBackgroundResource(R.drawable.img_1); // 
Reference to the play icon 
        } else { 
            mediaPlayer.start(); 
            playButton.setBackgroundResource(R.drawable.img_2); // 
Reference to the pause icon 
            updateSeekBar(); 
        } 
    } 
 
    private void playNextSong() { 
        if (mediaPlayer != null) { 
            mediaPlayer.stop(); 
            mediaPlayer.release(); 
        } 
        currentSongIndex = (currentSongIndex + 1) % songs.length; 
        initializeMediaPlayer(); 
        mediaPlayer.start(); 
        playButton.setBackgroundResource(R.drawable.img_2); // Change 
button to pause 
        updateSeekBar(); 
    } 
 
    private void playPreviousSong() { 
        if (mediaPlayer != null) { 
            mediaPlayer.stop(); 
            mediaPlayer.release(); 
        } 
        currentSongIndex = (currentSongIndex - 1 + songs.length) % 
songs.length; 
        initializeMediaPlayer(); 
mediaPlayer.start(); 
playButton.setBackgroundResource(R.drawable.img_2); // Change 
button to pause 
updateSeekBar(); 
} 
private void updateSeekBar() { 
isSeekBarUpdating = true; 
handler.postDelayed(new Runnable() { 
@Override 
public void run() { 
if (mediaPlayer != null && mediaPlayer.isPlaying()) { 
seekBar.setProgress(mediaPlayer.getCurrentPosition()); 
} 
if (isSeekBarUpdating) { 
handler.postDelayed(this, 1000); 
} 
} 
}, 1000); 
} 
@Override 
protected void onDestroy() { 
super.onDestroy(); 
isSeekBarUpdating = false;  // Stop updating SeekBar 
if (mediaPlayer != null) { 
mediaPlayer.release(); 
mediaPlayer = null; 
} 
} 
}