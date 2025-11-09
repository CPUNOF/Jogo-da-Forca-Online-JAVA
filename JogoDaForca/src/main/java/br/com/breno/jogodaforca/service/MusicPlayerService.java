package br.com.breno.jogodaforca.service; // Verifique seu pacote

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class MusicPlayerService {

    private static final Logger logger = Logger.getLogger(MusicPlayerService.class.getName());
    private static MusicPlayerService instance;

    private final List<Media> playlist = new ArrayList<>();
    private final List<String> nomesMusicas = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private int musicaAtualIndex = 0;
    
    //A lista de nomes de músicas
    private final String[] NOMES_ARQUIVOS_MUSICAS = {
        "AC_DC - Hells Bells (Instrumental).mp3",
        "New Super Mario Bros. Music - Starman _ Invincibility.mp3",
        "Super Mario Bros (NES) Music - Overworld Theme.mp3",
        "Zelda Original Soundtrack - Saria's Song (The Lost Woods).mp3"
    };


    private final DoubleProperty progressoAtual = new SimpleDoubleProperty(0);
    private boolean isPlaying = false;

    
    private MusicPlayerService() {
        new JFXPanel(); // Acorda o JavaFX
        carregarMusicas();
    }

    public static MusicPlayerService getInstance() {
        if (instance == null) {
            instance = new MusicPlayerService();
        }
        return instance;
    }

    private void carregarMusicas() {
        Platform.runLater(() -> {
            try {
                for (String nomeArquivo : NOMES_ARQUIVOS_MUSICAS) {
                    URL resource = getClass().getResource("/sounds/" + nomeArquivo);
                    if (resource == null) {
                        logger.warning("Música não encontrada: " + nomeArquivo);
                        continue;
                    }
                    Media media = new Media(resource.toURI().toString());
                    playlist.add(media);
                    nomesMusicas.add(nomeArquivo.replace(".mp3", "").replace("_", " "));
                }
                
                if (!playlist.isEmpty()) {
                    mediaPlayer = new MediaPlayer(playlist.get(musicaAtualIndex));
                    configurarMediaPlayer(); // Chama o método corrigido
                    logger.info("Player de música pronto. " + playlist.size() + " músicas carregadas.");
                } else {
                    logger.warning("Nenhuma música foi carregada. A pasta /sounds está vazia?");
                }

            } catch (Exception e) {
                logger.severe("Erro ao carregar músicas: " + e.getMessage());
            }
        });
    }
    
    /**
     * Configura o player atual (adiciona listeners)
     */
    private void configurarMediaPlayer() {
        mediaPlayer.setOnEndOfMedia(() -> proximaMusica());
        
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (mediaPlayer.getTotalDuration() != null) {
                double progresso = newTime.toSeconds() / mediaPlayer.getTotalDuration().toSeconds();
                progressoAtual.set(progresso);
            }
        });

        mediaPlayer.setOnReady(() -> {
            mediaPlayer.setVolume(1.0);
            playPause(); 
        });
    }
    
    // Controle

    public void playPause() {
        Platform.runLater(() -> {
            if (mediaPlayer == null) return;
            
            if (isPlaying) {
                mediaPlayer.pause();
                isPlaying = false;
            } else {
                mediaPlayer.play();
                isPlaying = true;
            }
        });
    }
    
    public void proximaMusica() {
        Platform.runLater(() -> {
            if (playlist.isEmpty()) return;
            musicaAtualIndex++;
            if (musicaAtualIndex >= playlist.size()) {
                musicaAtualIndex = 0; 
            }
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(playlist.get(musicaAtualIndex));
            configurarMediaPlayer();
        });
    }
    
    public void musicaAnterior() {
        Platform.runLater(() -> {
            if (playlist.isEmpty()) return;
            musicaAtualIndex--;
            if (musicaAtualIndex < 0) {
                musicaAtualIndex = playlist.size() - 1; 
            }
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer(playlist.get(musicaAtualIndex));
            configurarMediaPlayer();
        });
    }
    
    public void setVolume(double valor) {
        Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(valor);
            }
        });
    }

    public void setProgresso(double progresso) {
        Platform.runLater(() -> {
            if (mediaPlayer != null && mediaPlayer.getTotalDuration() != null) {
                Duration novaPosicao = mediaPlayer.getTotalDuration().multiply(progresso);
                mediaPlayer.seek(novaPosicao);
            }
        });
    }
    
    
    public DoubleProperty getProgressoAtualProperty() {
        return progressoAtual;
    }
    
    public String getNomeMusicaAtual() {
        if (nomesMusicas.isEmpty() || musicaAtualIndex >= nomesMusicas.size()) {
            return "Nenhuma música carregada";
        }
        return nomesMusicas.get(musicaAtualIndex);
    }
}