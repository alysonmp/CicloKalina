/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.Conversao.ControlConverte;
import Control.Start;
import Model.ModelStart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Collections;
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
    
    private JLabel labelPressao = new JLabel("Pressão");
    
    private JLabel labelMassa = new JLabel("Massa");
    
    private JLabel labelH = new JLabel("H");
    
    private JLabel labelS = new JLabel("S");
    
    private JLabel labelEfic = new JLabel("Eficiência");
    
    private JLabel labelTempEntr = new JLabel();
    
    private JLabel labelTempSai = new JLabel();
    
    private JLabel labelPressaoEntr = new JLabel();
    
    private JLabel labelPressaoSai = new JLabel();
    
    private JLabel labelDelta = new JLabel();
    
    private JLabel labelQcon = new JLabel();
    
    private JLabel labelVazio = new JLabel("<html><body><div style='text-align: center;'>Não existem dados<br> para serem mostrados</div></body></html>");
    
    private double P, T, M, H, S, TEntr, TSai, PEntr, PSai, Efic, Qcon, Eff;
    
    public ViewCaixaResposta(int x, int y, ModelStart start, String etapa){
        
        if(start == null){
            GridBagConstraints c = new GridBagConstraints();
            
            c.anchor = GridBagConstraints.CENTER;
            
            painelCaixa.setLayout(new GridBagLayout());
            
            painelCaixa.add(labelVazio, c);
            
            painelCaixa.setPreferredSize(new Dimension(250, 100));
            painelCaixa.setBackground(new Color(255, 255, 204));

            frameCaixa.add(painelCaixa);
            frameCaixa.setUndecorated(true);
            frameCaixa.setShape(new RoundRectangle2D.Double(0, 0, 250, 125, 50, 50));
            frameCaixa.setResizable(false);
            frameCaixa.setVisible(true);
            
            return;
        }
        
        switch(etapa){
            case "1": 
                P = start.getP1();
                T = start.getT1();
                H = start.getH1();
                S = start.getS1();
                break;
             
            case "2": 
                P = start.getP2();
                T = start.getT2();
                H = start.getH2();
                S = start.getS2();
                break;
                
            case "3": 
                P = start.getP3();
                T = start.getT3();
                H = start.getH3();
                S = start.getS3();
                break;
                
            case "4": 
                P = start.getP4();
                T = start.getT4();
                H = start.getH4();
                S = start.getS4();
                break;
                
            case "5": 
                P = start.getP5();
                T = start.getT5();
                H = start.getH5();
                S = start.getS5();
                break;
                
            case "6": 
                P = start.getP6();
                T = start.getT6();
                H = start.getH6();
                S = start.getS6();
                break;
            
            default:
                caixaEquipamentos(x, y, start, etapa);
                return;
        }
        
        M = start.getM();
        
        ControlConverte controlConverte = new ControlConverte();
        
        P = controlConverte.round(P, 2);
        T = controlConverte.round(T, 2);
        H = controlConverte.round(H, 2);
        S = controlConverte.round(S, 2);
        M = controlConverte.round(M, 2);
        
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
        
        c.gridx = 0;
        c.gridy = 1;
        painelCaixa.add(labelPressao, c);
        
        c.gridx = 0;
        c.gridy = 2;
        painelCaixa.add(labelH, c);
        
        c.gridx = 0;
        c.gridy = 3;
        painelCaixa.add(labelS, c);
        
        c.gridx = 0;
        c.gridy = 4;
        painelCaixa.add(labelMassa, c);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ViewCaixaResposta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        painelCaixa.setPreferredSize(new Dimension(250, 125));
        painelCaixa.setBackground(new Color(255, 255, 204));
        
        frameCaixa.add(painelCaixa);
        frameCaixa.setUndecorated(true);
        frameCaixa.setShape(new RoundRectangle2D.Double(0, 0, 250, 125, 50, 50));
        frameCaixa.setResizable(false);
        frameCaixa.setVisible(true);
    }

    private void caixaEquipamentos(int x, int y, ModelStart start, String etapa) {
        GridBagConstraints c = new GridBagConstraints();
        ControlConverte controlConverte = new ControlConverte();
        
        ArrayList<JLabel> labelList = new ArrayList<>();
        
        switch(etapa){
            case "bomba":
                TEntr = start.getT4();
                TSai = start.getT5();
                PEntr = start.getP4();
                PSai = start.getP5();
                
                Efic = start.getBeff();
                Efic = controlConverte.round(Efic, 2);
                
                labelEfic.setText("Eficiência = "+Efic);
                labelDelta.setText("∆ de Pressão = "+(controlConverte.round(PSai-PEntr, 2)));
                
                labelList.add(labelEfic);
                labelList.add(labelDelta);
                
                break;
                
            case "condensador":
                TEntr = start.getT3();
                TSai = start.getT4();
                PEntr = start.getP3();
                PSai = start.getP4();
                
                Qcon = start.getQCon();
                Qcon = controlConverte.round(Qcon, 2);
                
                Eff = start.getEff();
                Eff = controlConverte.round(Eff, 2);
                
                labelQcon.setText("Calor de Condensação = "+Qcon);
                labelEfic.setText("Eficiência = "+Eff);
                
                labelList.add(labelQcon);
                labelList.add(labelEfic);
                
            case "evaporador":
                TEntr = start.getT6();
                TSai = start.getT1();
                PEntr = start.getP6();
                PSai = start.getP1();
                
                labelDelta.setText("∆ de Pressão = "+(controlConverte.round(PSai-PEntr, 2)));
                
                labelList.add(labelDelta);
                
            case "regenerador":
                TEntr = start.getT2();
                TSai = start.getT3();
                PEntr = start.getP2();
                PSai = start.getP3();
                
                TEntr = controlConverte.round(TEntr, 2);
                TSai = controlConverte.round(TSai, 2);
                PEntr = controlConverte.round(PEntr, 2);
                PSai = controlConverte.round(PSai, 2);
                
                JLabel labelTempEntr1 = new JLabel();
                JLabel labelTempSai1 = new JLabel();
                JLabel labelPressaoEntr1 = new JLabel();
                JLabel labelPressaoSai1 = new JLabel();
                
                labelTempEntr1.setText("T Entrada Fluido = "+TEntr);
                labelTempSai1.setText("T Saída Fluido = "+TSai);
                labelPressaoEntr1.setText("P Entrada Fluido = "+PEntr);
                labelPressaoSai1.setText("P Saída Fluido = "+PSai);
                
                labelList.add(labelPressaoSai1);
                labelList.add(labelPressaoEntr1);
                labelList.add(labelTempSai1);
                labelList.add(labelTempEntr1);
                
                TEntr = start.getT5();
                TSai = start.getT6();
                PEntr = start.getP5();
                PSai = start.getP6();
                
                Eff = start.getEff();
                Eff = controlConverte.round(Eff, 2);
                
                labelEfic.setText("Efetividade = "+Eff);
                
                labelList.add(labelEfic);
                
            case "turbina":
                TEntr = start.getT1();
                TSai = start.getT2();
                PEntr = start.getP1();
                PSai = start.getP2();
                
                Eff = start.getEff();
                Eff = controlConverte.round(Eff, 2);
                
                labelEfic.setText("Eficiência = "+Eff);
                
                labelList.add(labelEfic);
        }
        
        M = start.getM();
        
        TEntr = controlConverte.round(TEntr, 2);
        TSai = controlConverte.round(TSai, 2);
        PEntr = controlConverte.round(PEntr, 2);
        PSai = controlConverte.round(PSai, 2);
        M = controlConverte.round(M, 2);
        
        painelCaixa.setLayout(new GridBagLayout());
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ViewCaixaResposta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        labelTempEntr.setText("Temperatura de Entrada = "+TEntr);
        labelTempSai.setText("Temperatura de Saída = "+TSai);
        labelPressaoEntr.setText("Pressão de Entrada = "+PEntr);
        labelPressaoSai.setText("Pressão de Saída = "+PSai);
        labelMassa.setText("Massa = "+M);
        
        labelList.add(labelMassa);
        labelList.add(labelPressaoSai);
        labelList.add(labelPressaoEntr);
        labelList.add(labelTempSai);
        labelList.add(labelTempEntr);
        
        Collections.reverse(labelList);
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 0, 0, 15);
        
        for(int i = 0; i < labelList.size(); i++){
            c.gridx = 0;
            c.gridy = i;        
            painelCaixa.add(labelList.get(i), c);
        }
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ViewCaixaResposta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        painelCaixa.setPreferredSize(new Dimension(250, 150));
        painelCaixa.setBackground(new Color(255, 255, 204));
        
        frameCaixa.add(painelCaixa);
        frameCaixa.setUndecorated(true);
        frameCaixa.setShape(new RoundRectangle2D.Double(0, 0, 250, 125, 50, 50));
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
