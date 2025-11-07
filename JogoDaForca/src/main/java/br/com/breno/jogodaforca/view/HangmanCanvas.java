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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
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
    private static final BasicStroke PINCEL_FORCA = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke PINCEL_CORDA = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private static final BasicStroke PINCEL_BONECO = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    private int etapa = 0;
    private Color corFundoAtual = COR_FUNDO_PADRAO;
    private Timer timerAnimacaoAcerto;

    private Timer timerAnimacaoMorte;
    private Timer timerAnimacaoErro;
    private double anguloBalanço = 0;
    private int direcaoBalanço = 1;

    public HangmanCanvas() {
        setBackground(COR_FUNDO_PADRAO);

        timerAnimacaoMorte = new Timer(16, e -> {
            anguloBalanço += direcaoBalanço * 0.005;
            if (anguloBalanço > 0.1 || anguloBalanço < -0.1) {
                direcaoBalanço *= -1;
            }
            repaint();
        });

        timerAnimacaoErro = new Timer(100, e -> {
            corFundoAtual = COR_FUNDO_ERRO;
            repaint();

            Timer voltaBranco = new Timer(150, e2 -> {
                corFundoAtual = COR_FUNDO_PADRAO;
                repaint();
                ((Timer) e2.getSource()).stop();
            });
            voltaBranco.setRepeats(false);
            voltaBranco.start();

            ((Timer) e.getSource()).stop();
        });
        timerAnimacaoErro.setRepeats(false);

        timerAnimacaoAcerto = new Timer(100, e -> {
            corFundoAtual = COR_FUNDO_ACERTO;
            repaint();

            Timer voltaBranco = new Timer(150, e2 -> {
                corFundoAtual = COR_FUNDO_PADRAO;
                repaint();
                ((Timer) e2.getSource()).stop();
            });
            voltaBranco.setRepeats(false);
            voltaBranco.start();
            ((Timer) e.getSource()).stop();
        });
        timerAnimacaoAcerto.setRepeats(false);
    }

    public void setEtapa(int novaEtapa) {

        if (novaEtapa > 0 && novaEtapa < 6 && this.etapa == novaEtapa) {
            timerAnimacaoErro.start();
            return;
        }
        this.etapa = novaEtapa;
        paraAnimacoes();

        if (novaEtapa == 6) {
            //timerAnimacaoMorte.start();
            timerAnimacaoErro.start();
        } else if (novaEtapa > 0) {
            timerAnimacaoErro.start();
        }

        repaint();
    }

    public void reset() {
        paraAnimacoes();
        corFundoAtual = COR_FUNDO_PADRAO;
        setEtapa(0);
    }

    public void acionarAnimacaoAcerto() {
        if (timerAnimacaoAcerto != null && !timerAnimacaoAcerto.isRunning()) {
            timerAnimacaoAcerto.start();
        }
    }

    private void paraAnimacoes() {
        if (timerAnimacaoMorte.isRunning()) {
            timerAnimacaoMorte.stop();
        }
        if (timerAnimacaoErro.isRunning()) {
            timerAnimacaoErro.stop();
        }
        if (timerAnimacaoAcerto != null && timerAnimacaoAcerto.isRunning()) {
        timerAnimacaoAcerto.stop();
        }
        anguloBalanço = 0;
        direcaoBalanço = 1;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(corFundoAtual);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(corFundoAtual);
        g.fillRect(0, 0, getWidth(), getHeight());

        int w = getWidth();
        int h = getHeight();
        int baseY = h - 40; // chao
        int centroForcaX = w / 2 - 50; //poste vertical
        int vigaTopoY = baseY - 250; // viga top

        //corda
        int cordaX = centroForcaX + 100;
        int cordaTopoY = vigaTopoY;

        //Cabeça
        int cabecaTopoY = cordaTopoY + 30;
        int cabecaDiam = 50;
        int cabecaCentroX = cordaX;
        int cabecaCentroY = cabecaTopoY + (cabecaDiam / 2);

        // parte do chao
        g2.setStroke(PINCEL_FORCA);
        g2.setColor(COR_FORCA);
        g2.drawLine(centroForcaX - 100, baseY, centroForcaX + 150, baseY); //chao
        // vareta lateral
        g2.drawLine(centroForcaX, baseY, centroForcaX, vigaTopoY);
        // vareta de cima 
        g2.drawLine(centroForcaX, vigaTopoY, cordaX, vigaTopoY);

        AffineTransform oldTransform = g2.getTransform();

        if (etapa == 6 && timerAnimacaoMorte.isRunning()) {
            g2.rotate(anguloBalanço, cordaX, cordaTopoY);
        }

        //corda desenho
        g2.setStroke(PINCEL_CORDA);
        g2.setColor(Color.GRAY.darker());
        g2.drawLine(cordaX, cordaTopoY, cordaX, cabecaTopoY);

        if (etapa >= 1) { //cabeça
            g2.setStroke(PINCEL_BONECO);
            g2.setColor(Color.BLACK);

            g2.drawOval(cabecaCentroX - (cabecaDiam / 2), cabecaTopoY, cabecaDiam, cabecaDiam);
        }
        if (etapa >= 2) { //corpo
            g2.setColor(Color.BLACK);
            g2.drawLine(cabecaCentroX, cabecaTopoY + cabecaDiam, cabecaCentroX, cabecaTopoY + 140); // Corpo
        }
        if (etapa >= 3) { // braço d
            g2.setColor(Color.RED.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 60, cabecaCentroX + 40, cabecaTopoY + 100);
        }
        if (etapa >= 4) { //braço e
            g2.setColor(Color.RED.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 60, cabecaCentroX - 40, cabecaTopoY + 100);
        }
        if (etapa >= 5) { //perna d
            g2.setColor(Color.BLUE.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 140, cabecaCentroX + 30, cabecaTopoY + 190);
        }
        if (etapa >= 6) { //perna e
            g2.setColor(Color.BLUE.darker());
            g2.drawLine(cabecaCentroX, cabecaTopoY + 140, cabecaCentroX - 30, cabecaTopoY + 190);
        }

        if (etapa == 6) { // rosto de morto
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            g2.drawLine(cabecaCentroX - 10, cabecaCentroY - 10, cabecaCentroX - 4, cabecaCentroY - 4);
            g2.drawLine(cabecaCentroX - 10, cabecaCentroY - 4, cabecaCentroX - 4, cabecaCentroY - 10);
            g2.drawLine(cabecaCentroX + 4, cabecaCentroY - 10, cabecaCentroX + 10, cabecaCentroY - 4);
            g2.drawLine(cabecaCentroX + 4, cabecaCentroY - 4, cabecaCentroX + 10, cabecaCentroY - 10);
            //boca triste
            g2.drawArc(cabecaCentroX - 10, cabecaCentroY + 5, 20, 10, 0, -180);
        } else if (etapa >= 1) {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            g2.fillOval(cabecaCentroX - 10, cabecaCentroY - 7, 5, 5);
            g2.fillOval(cabecaCentroX + 5, cabecaCentroY - 7, 5, 5);
            g2.drawLine(cabecaCentroX - 7, cabecaCentroY + 8, cabecaCentroX + 7, cabecaCentroY + 8);
        }

        g2.setTransform(oldTransform);
    }
}
