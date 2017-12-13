package fr.matthis974jump.musicbot.music;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

public class AudioListener extends AudioEventAdapter{

    private final BlockingQueue<AudioTrack> tracks = new LinkedBlockingQueue<AudioTrack>();
    private final MusicPlayer player;

    public AudioListener(MusicPlayer player){
        this.player = player;
    }

    public BlockingQueue<AudioTrack> getTracks() {
        return tracks;
    }

    public int getTrackSize(){
        return tracks.size();
    }

    public void nextTrack(){
        if(tracks.isEmpty()){
            if(player.getGuild().getAudioManager().getConnectedChannel() != null)
                player.getGuild().getAudioManager().closeAudioConnection();
            return;
        }
        player.getAudioPlayer().startTrack(tracks.poll(), false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
            this.player.getTextChannel().sendMessage("Next Track : "+track.getInfo().title+"\n Queue size : "+tracks.size()).queue();
            this.tracks.add(track);

        }
    }

    public void queue(AudioTrack track) {
        if (!player.getAudioPlayer().startTrack(track, true)) tracks.offer(track);
    }

}