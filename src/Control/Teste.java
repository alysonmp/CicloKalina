/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Control.Conversao.ControlConverte;
import Model.ModelDRT70;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.hibernate.Session;

/**
 *
 * @author alysonmp
 */
public class Teste {
    
    public Teste(Session session, ControlPrincipal ctrlPrincipal){
        String csvFile = "src/Csv/teste.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
        int linha = 1;
        ControlConverte converte = new ControlConverte();
        
        try{
            br = new BufferedReader(new FileReader(csvFile));
            while((line = br.readLine()) != null){
                double[] valores = new double[40];
                String[] table_c = line.split(csvSplitBy);
                for(int i = 0; i < table_c.length; i++){
                    valores[i] = Double.parseDouble(table_c[i]);
                }
                
                System.out.println("\n\n"+valores[0]+" "+(int)valores[5]+" "+(int)valores[7]+" "+valores[3]+" "+valores[2]+" "+valores[1]+" "+valores[6]+" "+valores[4]);
                Start start = new Start((int)valores[5], (int)valores[7], valores[3], 1000, valores[2], valores[1], 313.15, valores[6]*100, valores[4], session, ctrlPrincipal);

                System.out.println(converte.round(start.getAT(), 3) +" "+converte.round(valores[8], 3));
                System.out.println(converte.round(start.getQevp(), 3)+" "+converte.round(valores[19], 3));
                System.out.println(converte.round(start.getQcon(), 3)+" "+converte.round(valores[20], 3));
                System.out.println(converte.round(start.getQreg(), 3) +" "+converte.round(valores[21], 3));
                System.out.println(converte.round(start.getWn(), 3) +" "+converte.round(valores[33], 3));
                
                if(converte.round(start.getAT(), 3) == converte.round(valores[8], 3) && converte.round(start.getQcon(), 3) == converte.round(valores[19], 3) && converte.round(start.getQevp(),3) == converte.round(valores[20], 3) && converte.round(start.getQreg(), 3) == converte.round(valores[21], 3) && converte.round(start.getWn(), 3) == converte.round(valores[33], 3)){
                    System.out.println("linha "+linha+" correta");
                }
                linha++;
                
            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}
