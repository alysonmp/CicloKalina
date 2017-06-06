/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Start;
import Model.ModelStart;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author alysonmp
 */
public class ViewCaixaResposta {
    
    private JFrame frameCaixa = new JFrame();
    
    private JPanel painelCaixa = new JPanel();
    
    private JLabel labelTemp = new JLabel("Temperatura");
    private JLabel fieldTemp = new JLabel();
    
    private JLabel labelPressao = new JLabel("Pressão");
    private JLabel fieldPressao = new JLabel();
    
    private JLabel labelMassa = new JLabel("Massa");
    private JLabel fieldMassa = new JLabel();
    
    private JLabel labelH = new JLabel("H");
    private JLabel fieldH = new JLabel();
    
    private JLabel labelS = new JLabel("S");
    private JLabel fieldS = new JLabel();
    
    private JLabel labelVazio = new JLabel("<html><body><div style='text-align: center;'>Não existem dados<br> para serem mostrados</div></body></html>");
    
    private double P, T, M, H, S;
    
    public ViewCaixaResposta(int x, int y, ModelStart start, int etapa){
        
        if(start == null){
            painelCaixa.add(labelVazio);
            
            painelCaixa.setPreferredSize(new Dimension(250, 100));
            painelCaixa.setBackground(new Color(255, 255, 204));

            frameCaixa.add(painelCaixa);
            frameCaixa.setUndecorated(true);
            frameCaixa.setShape(new RoundRectangle2D.Double(0, 0, 250, 100, 50, 50));
            frameCaixa.setResizable(false);
            frameCaixa.setVisible(true);
            
            return;
        }
        
        System.out.println("etapa = "+etapa);
        
        switch(etapa){
            case 1: 
                P = start.getP1();
                T = start.getT1();
                H = start.getH1();
                S = start.getS1();
                break;
             
            case 2: 
                P = start.getP2();
                T = start.getT2();
                H = start.getH2();
                S = start.getS2();
                break;
                
            case 3: 
                P = start.getP3();
                T = start.getT3();
                H = start.getH3();
                S = start.getS3();
                break;
                
            case 4: 
                P = start.getP4();
                T = start.getT4();
                H = start.getH4();
                S = start.getS4();
                break;
                
            case 5: 
                P = start.getP5();
                T = start.getT5();
                H = start.getH5();
                S = start.getS5();
                break;
                
            case 6: 
                P = start.getP6();
                T = start.getT6();
                H = start.getH6();
                S = start.getS6();
                break;
        }
        
        M = start.getM();
        
        painelCaixa.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ViewCaixaResposta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        labelTemp.setText("Temperatura = "+T);
        labelPressao.setText("Pressão = "+P);
        labelH.setText("H = "+H);
        labelS.setText("S = "+S);
        labelMassa.setText("Massa = "+M);
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 15);
        painelCaixa.add(labelTemp, c);
        
        c.gridx = 1;
        c.gridy = 0;
        painelCaixa.add(fieldTemp, c);
        
        c.gridx = 0;
        c.gridy = 1;
        painelCaixa.add(labelPressao, c);
        
        c.gridx = 1;
        c.gridy = 1;
        painelCaixa.add(fieldPressao, c);
        
        c.gridx = 0;
        c.gridy = 2;
        painelCaixa.add(labelH, c);
        
        c.gridx = 1;
        c.gridy = 2;
        painelCaixa.add(fieldH, c);
        
        c.gridx = 0;
        c.gridy = 3;
        painelCaixa.add(labelS, c);
        
        c.gridx = 1;
        c.gridy = 3;
        painelCaixa.add(fieldS, c);
        
        c.gridx = 0;
        c.gridy = 4;
        painelCaixa.add(labelMassa, c);
        
        c.gridx = 1;
        c.gridy = 4;
        painelCaixa.add(labelMassa, c);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ViewCaixaResposta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        painelCaixa.setPreferredSize(new Dimension(250, 100));
        painelCaixa.setBackground(new Color(255, 255, 204));
        
        frameCaixa.add(painelCaixa);
        frameCaixa.setUndecorated(true);
        frameCaixa.setShape(new RoundRectangle2D.Double(0, 0, 250, 100, 50, 50));
        frameCaixa.setResizable(false);
        frameCaixa.setVisible(true);
    }

    public JFrame getFrameCaixa() {
        return frameCaixa;
    }

    public void setFrameCaixa(JFrame frameCaixa) {
        this.frameCaixa = frameCaixa;
    }
    
}
