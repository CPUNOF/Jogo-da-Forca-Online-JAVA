
package br.com.breno.jogodaforca.view;

import br.com.breno.jogodaforca.model.Palavra;
import br.com.breno.jogodaforca.service.GameService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends javax.swing.JPanel {

    private final GameService gameService;
    private final HangmanCanvas canvas;
    
    public GamePanel() {
        initComponents();
        this.gameService = new GameService();
        this.canvas = new HangmanCanvas();

        // Adiciona o canvas na área da forca
        panelForca.setLayout(new java.awt.BorderLayout());
        panelForca.add(canvas, java.awt.BorderLayout.CENTER);

        iniciarJogo();

        btnTentar.addActionListener(e -> tentar());
    }
    
    private void tentar() {
        if (txtLetra.getText().isEmpty()) return;

        char letra = txtLetra.getText().toUpperCase().charAt(0);
        boolean acertou = gameService.tentarLetra(letra);

        lblPalavra.setText(gameService.getPalavraParcial());
        lblErros.setText("Erros: " + gameService.getLetrasErradas() + "/6");

        if (!acertou) {
            canvas.setEtapa(gameService.getLetrasErradas().size());
        }

        if (gameService.perdeu()) {
            JOptionPane.showMessageDialog(this, "💀 Você perdeu! Palavra: " + gameService.getPalavraAtual().getTexto());
            iniciarJogo();
        } else if (gameService.venceu()) {
            JOptionPane.showMessageDialog(this, "🎉 Você venceu!");
            iniciarJogo();
        }

        txtLetra.setText("");
        txtLetra.requestFocus();
    }
    
    private void iniciarJogo() {
        List<Palavra> palavras = new ArrayList<>();
        palavras.add(new Palavra("Animais", "JACARE"));
        palavras.add(new Palavra("Frutas", "BANANA"));
        palavras.add(new Palavra("Objetos", "CADEIRA"));
        palavras.add(new Palavra("Cores", "AZUL"));
        palavras.add(new Palavra("Paises", "BRASIL"));

        gameService.iniciarJogo(palavras);

        lblPalavra.setText(gameService.getPalavraParcial());
        lblErros.setText("Erros: 0/6");
        canvas.setEtapa(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        txtLetra = new javax.swing.JTextField();
        panelForca = new javax.swing.JPanel();
        lblErros = new javax.swing.JLabel();
        lblPalavra = new javax.swing.JLabel();
        btnTentar = new javax.swing.JButton();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        layout.rowHeights = new int[] {0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0, 11, 0};
        setLayout(layout);

        txtLetra.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        txtLetra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLetra.setPreferredSize(new java.awt.Dimension(100, 30));
        txtLetra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLetraActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 7;
        add(txtLetra, gridBagConstraints);

        panelForca.setPreferredSize(new java.awt.Dimension(300, 300));

        javax.swing.GroupLayout panelForcaLayout = new javax.swing.GroupLayout(panelForca);
        panelForca.setLayout(panelForcaLayout);
        panelForcaLayout.setHorizontalGroup(
            panelForcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelForcaLayout.setVerticalGroup(
            panelForcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.gridheight = 5;
        add(panelForca, gridBagConstraints);

        lblErros.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        lblErros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblErros.setText("Erros: [] / 6");
        lblErros.setPreferredSize(new java.awt.Dimension(400, 26));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.gridheight = 3;
        add(lblErros, gridBagConstraints);

        lblPalavra.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        lblPalavra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPalavra.setText("_ _ _ _ _ _ _");
        lblPalavra.setPreferredSize(new java.awt.Dimension(400, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 11;
        gridBagConstraints.gridheight = 3;
        add(lblPalavra, gridBagConstraints);

        btnTentar.setText("Tentar Letra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.insets = new java.awt.Insets(0, 24, 0, 8);
        add(btnTentar, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void txtLetraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLetraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLetraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTentar;
    private javax.swing.JLabel lblErros;
    private javax.swing.JLabel lblPalavra;
    private javax.swing.JPanel panelForca;
    private javax.swing.JTextField txtLetra;
    // End of variables declaration//GEN-END:variables
}
