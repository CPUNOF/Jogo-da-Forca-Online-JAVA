package br.com.breno.jogodaforca.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HangmanCanvas extends JPanel {

    private int etapa = 0; // De 0 a 6 
    private float animacaoProgresso = 0f;
    private Timer timerAnimacao;

    public HangmanCanvas() {
        setBackground(Color.WHITE);
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
        iniciarAnimacao();
    }

    private void iniciarAnimacao() {
        if (timerAnimacao != null && timerAnimacao.isRunning()) {
            timerAnimacao.stop();
        }

        animacaoProgresso = 0f;

        // Timer de animação (60 FPS)??? 
        timerAnimacao = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animacaoProgresso += 0.05f;
                if (animacaoProgresso >= 1f) {
                    animacaoProgresso = 1f;
                    timerAnimacao.stop();
                }
                repaint();
            }
        });

        timerAnimacao.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //Suaizar as Linhas
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(4));

        int w = getWidth();
        int h = getHeight();

        int baseY = h - 50;
        int centroX = w / 2;

        g2.setColor(Color.BLACK);

        //Desenha o forca
        g2.drawLine(centroX - 100, baseY, centroX + 100, baseY); //base
        g2.drawLine(centroX - 50, baseY, centroX - 50, baseY - 250); // para o poste Vertical
        g2.drawLine(centroX - 50, baseY - 250, centroX + 50, baseY - 250); // poste do superior
        g2.drawLine(centroX + 50, baseY - 250, centroX + 50, baseY - 220); // Corda

        //Desenhar o boneco conforme as etapas
        g2.setColor(Color.BLUE);

        //Valor animacao Progresso serve para desenhar cada parte de modo suave. 
        switch (etapa) {
            case 1 ->
                g2.drawOval(centroX + 25, baseY - 220, 50, 50);
            case 2 -> {
                g2.drawOval(centroX + 25, baseY - 220, 50, 50);
                g2.drawLine(centroX + 50, baseY - 170, centroX + 50, baseY - 100);
            }
            case 3 -> {
                g2.drawOval(centroX + 25, baseY - 220, 50, 50);
                g2.drawLine(centroX + 50, baseY - 170, centroX + 50, baseY - 100);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 20, baseY - 130);
            }
            case 4 -> {
                g2.drawOval(centroX + 25, baseY - 220, 50, 50);
                g2.drawLine(centroX + 50, baseY - 170, centroX + 50, baseY - 100);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 20, baseY - 130);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 80, baseY - 130);
            }
            case 5 -> {
                g2.drawOval(centroX + 25, baseY - 220, 50, 50);
                g2.drawLine(centroX + 50, baseY - 170, centroX + 50, baseY - 100);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 20, baseY - 130);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 80, baseY - 130);
                g2.drawLine(centroX + 50, baseY - 100, centroX + 20, baseY - 50);
            }
            case 6 -> {
                double angulo = Math.sin(System.currentTimeMillis() / 200.0) * 5; // balança levemente
                g2.rotate(Math.toRadians(angulo), centroX + 50, baseY - 220);
                g2.drawOval(centroX + 25, baseY - 220, 50, 50);
                g2.drawLine(centroX + 50, baseY - 170, centroX + 50, baseY - 100);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 20, baseY - 130);
                g2.drawLine(centroX + 50, baseY - 160, centroX + 80, baseY - 130);
                g2.drawLine(centroX + 50, baseY - 100, centroX + 20, baseY - 50);
                g2.drawLine(centroX + 50, baseY - 100, centroX + 80, baseY - 50);
            }

        }

    }

}
