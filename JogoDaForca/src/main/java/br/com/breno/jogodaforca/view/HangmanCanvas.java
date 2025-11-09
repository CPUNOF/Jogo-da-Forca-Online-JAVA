/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.breno.jogodaforca.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author breno
 */
public class HangmanCanvas extends JPanel {

    private static final Color COR_FUNDO_PADRAO = Color.WHITE;
    private static final Color COR_FUNDO_ERRO = new Color(255, 200, 200);
    private static final Color COR_FUNDO_ACERTO = new Color(200, 255, 200);
    private static final Color COR_FORCA = Color.BLACK;
    private static final Color COR_PINGO = new Color(180, 0, 0); 
    private static final BasicStroke PINCEL_FORCA = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke PINCEL_CORDA = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke PINCEL_BONECO = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);


    private int etapa = 0;
    private Color corFundoAtual = COR_FUNDO_PADRAO;


    private Timer timerAnimacaoMorte;
    private Timer timerAnimacaoErro;
    private Timer timerAnimacaoAcerto;
    private double anguloBalanço = 0;
    private int direcaoBalanço = 1;
    
    private final List<Pingo> pingos = new ArrayList<>();
    private final Random random = new Random();

    public HangmanCanvas() {
        setBackground(COR_FUNDO_PADRAO);
        
        timerAnimacaoMorte = new Timer(16, e -> {
            anguloBalanço += direcaoBalanço * 0.005;
            if (anguloBalanço > 0.1 || anguloBalanço < -0.1) {
                direcaoBalanço *= -1;
            }
            

            if (random.nextFloat() > 0.85) {
                pingos.add(new Pingo(getWidth() / 2 + 50, getHeight() - 40));
            }
            
            List<Pingo> pingosParaRemover = new ArrayList<>();
            for (Pingo p : pingos) {
                p.atualizar();
                if (p.estaInvisivel()) {
                    pingosParaRemover.add(p);
                }
            }
            pingos.removeAll(pingosParaRemover);
            
            repaint();
        });

        timerAnimacaoErro = new Timer(100, e -> {
            corFundoAtual = COR_FUNDO_ERRO;
            repaint();
            Timer voltaBranco = new Timer(150, e2 -> {
                corFundoAtual = COR_FUNDO_PADRAO;
                repaint();
                ((Timer)e2.getSource()).stop();
            });
            voltaBranco.setRepeats(false);
            voltaBranco.start();
            ((Timer)e.getSource()).stop();
        });
        timerAnimacaoErro.setRepeats(false);
        
        timerAnimacaoAcerto = new Timer(100, e -> {
            corFundoAtual = COR_FUNDO_ACERTO;
            repaint();
            Timer voltaBranco = new Timer(150, e2 -> {
                corFundoAtual = COR_FUNDO_PADRAO;
                repaint();
                ((Timer)e2.getSource()).stop();
            });
            voltaBranco.setRepeats(false);
            voltaBranco.start();
            ((Timer)e.getSource()).stop();
        });
        timerAnimacaoAcerto.setRepeats(false);
    }
    
    public void setEtapa(int novaEtapa) {
        if (novaEtapa > 0 && novaEtapa < 6 && this.etapa == novaEtapa) {
             timerAnimacaoErro.start();
             return;
        }
        this.etapa = novaEtapa;
        pararAnimacoes();
        
        if (novaEtapa == 8) {
            timerAnimacaoMorte.start(); 
        } else if (novaEtapa > 0) {
            timerAnimacaoErro.start();
        }
        repaint();
    }
    
    public void acionarAnimacaoAcerto() {
        pararAnimacoes();
        if (timerAnimacaoAcerto != null && !timerAnimacaoAcerto.isRunning()) {
            timerAnimacaoAcerto.start();
        }
    }
    
    public void reset() {
        pararAnimacoes();
        corFundoAtual = COR_FUNDO_PADRAO;
        setEtapa(0);
    }

    private void pararAnimacoes() {
        if (timerAnimacaoMorte.isRunning()) timerAnimacaoMorte.stop();
        if (timerAnimacaoErro.isRunning()) timerAnimacaoErro.stop();
        if (timerAnimacaoAcerto != null && timerAnimacaoAcerto.isRunning()) timerAnimacaoAcerto.stop();
        anguloBalanço = 0;
        direcaoBalanço = 1;
        pingos.clear(); 
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(corFundoAtual);
        g.fillRect(0, 0, getWidth(), getHeight());


        int w = getWidth(); int h = getHeight(); int baseY = h - 40;
        int centroForcaX = w / 2 - 50; int vigaTopoY = baseY - 250;
        int cordaX = centroForcaX + 100; int cordaTopoY = vigaTopoY;
        int cabecaTopoY = cordaTopoY + 30; int cabecaDiam = 50;
        int cabecaCentroX = cordaX; int cabecaCentroY = cabecaTopoY + (cabecaDiam / 2);
        
        g2.setColor(COR_FORCA); g2.setStroke(PINCEL_FORCA);
        g2.drawLine(centroForcaX - 100, baseY, centroForcaX + 150, baseY); // Chão
        g2.drawLine(centroForcaX, baseY, centroForcaX, vigaTopoY); // Poste
        g2.drawLine(centroForcaX, vigaTopoY, cordaX, vigaTopoY); // Viga

        AffineTransform oldTransform = g2.getTransform();
        if (etapa >= 8 && timerAnimacaoMorte.isRunning()) { // ✅ MUDADO DE 6 PARA 8
            g2.rotate(anguloBalanço, cordaX, cordaTopoY);
        }

        g2.setStroke(PINCEL_CORDA); g2.setColor(Color.GRAY.darker());
        g2.drawLine(cordaX, cordaTopoY, cordaX, cabecaTopoY);

        if (etapa >= 1) { // Etapa 1: Cabeça
            g2.setStroke(PINCEL_BONECO); g2.setColor(Color.BLACK);
            g2.drawOval(cabecaCentroX - (cabecaDiam/2), cabecaTopoY, cabecaDiam, cabecaDiam);
        }
        if (etapa >= 2) { // Etapa 2: Corpo
            g2.setColor(Color.BLACK);
            g2.drawLine(cabecaCentroX, cabecaTopoY + cabecaDiam, cabecaCentroX, cabecaTopoY + 140);
        }
        if (etapa >= 3) { // Etapa 3: Braço Direito
            g2.setColor(Color.RED.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 60, cabecaCentroX + 40, cabecaTopoY + 100);
        }
        if (etapa >= 4) { // Etapa 4: Braço Esquerdo
            g2.setColor(Color.RED.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 60, cabecaCentroX - 40, cabecaTopoY + 100);
        }
        if (etapa >= 5) { // Etapa 5: Perna Direita
            g2.setColor(Color.BLUE.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 140, cabecaCentroX + 30, cabecaTopoY + 190);
        }
        if (etapa >= 6) { // Etapa 6: Perna Esquerda
            g2.setColor(Color.BLUE.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 140, cabecaCentroX - 30, cabecaTopoY + 190);
        }

        if (etapa >= 7) {
            g2.setStroke(new BasicStroke(2)); g2.setColor(Color.BLACK);
            g2.fillOval(cabecaCentroX - 10, cabecaCentroY - 7, 5, 5); // Olho esq.
            g2.fillOval(cabecaCentroX + 5, cabecaCentroY - 7, 5, 5); // Olho dir.
            g2.drawLine(cabecaCentroX - 7, cabecaCentroY + 8, cabecaCentroX + 7, cabecaCentroY + 8); // Boca
        }
        if (etapa >= 8) { 
            g2.setStroke(new BasicStroke(2)); g2.setColor(Color.BLACK);
            g2.drawLine(cabecaCentroX - 10, cabecaCentroY - 10, cabecaCentroX - 4, cabecaCentroY - 4); 
            g2.drawLine(cabecaCentroX - 10, cabecaCentroY - 4, cabecaCentroX - 4, cabecaCentroY - 10);
            g2.drawLine(cabecaCentroX + 4, cabecaCentroY - 10, cabecaCentroX + 10, cabecaCentroY - 4);
            g2.drawLine(cabecaCentroX + 4, cabecaCentroY - 4, cabecaCentroX + 10, cabecaCentroY - 10);
            g2.drawArc(cabecaCentroX - 10, cabecaCentroY + 5, 20, 10, 0, -180); 
        }
        
        g2.setTransform(oldTransform);
        
        if (etapa >= 8) { 
            g2.setColor(COR_PINGO);
            for (Pingo p : pingos) {
                int xPingo = cabecaCentroX + p.getXOffset();
                int yPingo = cabecaTopoY + 200 + p.getY(); 
                int tamanho = (int) (6 * (1.0 - p.getProgresso()));
                if (tamanho < 1) tamanho = 1;
                g2.fillOval(xPingo, yPingo, tamanho, tamanho);
            }
        }
    }
    
    private static class Pingo {
        private int xOffset; 
        private int y;       
        private int velocidade;
        private int alturaMaxima;
        
        public Pingo(int centroX, int chaoY) {
            Random r = new Random();
            this.xOffset = r.nextInt(60) - 30; 
            this.y = 0; 
            this.velocidade = r.nextInt(3) + 2;
            this.alturaMaxima = 40; // 
        }
        
        public void atualizar() {
            y += velocidade;
        }
        
        public int getXOffset() { return xOffset; }
        public int getY() { return y; }
        public double getProgresso() { return (double) y / alturaMaxima; }
        public boolean estaInvisivel() { return y > alturaMaxima; }
    }
}
