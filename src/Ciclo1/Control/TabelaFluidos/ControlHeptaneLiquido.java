/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelHeptaneLiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

/**
 *
 * @author leonardo
 */
public class ControlHeptaneLiquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;

    public ControlHeptaneLiquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaHeptaneLiquido(){
        String csvFile = "src/Csv/HEPTANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelHeptaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] heptane_liquido = line.split(csvSplitBy);
                    
                    session.save(new ModelHeptaneLiquido(Double.parseDouble(heptane_liquido[0]), Double.parseDouble(heptane_liquido[1]), Double.parseDouble(heptane_liquido[2]), Double.parseDouble(heptane_liquido[3]), Double.parseDouble(heptane_liquido[4]), Double.parseDouble(heptane_liquido[5]),Double.parseDouble(heptane_liquido[6])));
                }
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
    
    public void interpolacao(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelHeptaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from heptane_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelHeptaneLiquido> heptane_liquido = consulta.list(); 
        ModelHeptaneLiquido heptane_liquido1 = heptane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from heptane_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneLiquido.class));//Sem isso aqui impossível de retornar
	heptane_liquido = consulta.list(); 
        ModelHeptaneLiquido heptane_liquido2 = heptane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from heptane_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneLiquido.class));//Sem isso aqui impossível de retornar
	heptane_liquido = consulta.list(); 
        ModelHeptaneLiquido heptane_liquido3 = heptane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from heptane_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHeptaneLiquido.class));//Sem isso aqui impossível de retornar
	heptane_liquido = consulta.list(); 
        ModelHeptaneLiquido heptane_liquido4 = heptane_liquido.get(0);
     
        double p  = ((pressao - heptane_liquido1.getPRESSAO())/(heptane_liquido3.getPRESSAO() - heptane_liquido1.getPRESSAO()));
        double t1 = ((temperatura - heptane_liquido1.getTEMPERATURA())/(heptane_liquido2.getTEMPERATURA() - heptane_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - heptane_liquido3.getTEMPERATURA())/(heptane_liquido4.getTEMPERATURA() - heptane_liquido3.getTEMPERATURA()));
        
        Cpl1 = heptane_liquido1.getCPL()+ (heptane_liquido2.getCPL()- heptane_liquido1.getCPL()) * t1;
        Cpl2 = heptane_liquido3.getCPL()+ (heptane_liquido4.getCPL()- heptane_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = heptane_liquido1.getPRL()+ (heptane_liquido2.getPRL()- heptane_liquido1.getPRL()) * t1;
        Prl2 = heptane_liquido3.getPRL()+ (heptane_liquido4.getPRL()- heptane_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = heptane_liquido1.getKL() + (heptane_liquido2.getKL() - heptane_liquido1.getKL()) * t1;
        kl2 = heptane_liquido3.getKL() + (heptane_liquido4.getKL() - heptane_liquido3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = heptane_liquido1.getMUL() + (heptane_liquido2.getMUL() - heptane_liquido1.getMUL()) * t1;
        Mul2 = heptane_liquido3.getMUL() + (heptane_liquido4.getMUL() - heptane_liquido3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = heptane_liquido1.getVCL() + (heptane_liquido2.getVCL() - heptane_liquido1.getVCL()) * t1;
        Vcl2 = heptane_liquido3.getVCL() + (heptane_liquido4.getVCL() - heptane_liquido3.getVCL()) * t2;
        Vcl = Vcl1 + (Vcl2 - Vcl1) * p;
    }

    public double getCpl() {
        return Cpl;
    }

    public void setCpl(double Cpl) {
        this.Cpl = Cpl;
    }

    public double getPrl() {
        return Prl;
    }

    public void setPrl(double Prl) {
        this.Prl = Prl;
    }

    public double getKl() {
        return kl;
    }

    public void setKl(double kl) {
        this.kl = kl;
    }

    public double getMul() {
        return Mul;
    }

    public void setMul(double Mul) {
        this.Mul = Mul;
    }

    public double getVcl() {
        return Vcl;
    }

    public void setVcl(double Vcl) {
        this.Vcl = Vcl;
    }
}
