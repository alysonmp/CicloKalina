/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.View;

import Ciclo1.Control.ControlPrincipal;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author alysonmp
 */
public class ViewCiclos extends JPanel{
    
    private ControlPrincipal ctrlPrincipal;
    BufferedImage image;
    int x, y;
    
    //FUNÇÃO QUE RECEBE A IMAGEM DO CICLO E A INSERE NO JPANEL
    public ViewCiclos(ControlPrincipal ctrlPrincipal, String path, int x, int y){
        this.ctrlPrincipal = ctrlPrincipal;
        this.x = x;
        this.y = y;
        
        try{
            image = ImageIO.read(new File(path));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    //FUNÇÃO QUE PINTA NA TELA A IMAGEM DO CICLO CORRESPONDENTE
    @Override
    protected void paintComponent(Graphics g) {
        setOpaque(false);
        setBackground(Color.white);
        g.drawImage(image, 0, 0, this.x, this.y, null);
    }
}
