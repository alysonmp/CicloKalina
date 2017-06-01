/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR11Liquido;
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
 * @author alysonmp
 */
public class ControlR11Liquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR11Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR11Liquido(){
        String csvFile = "src/Csv/R11_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR11Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R11_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR11Liquido(Double.parseDouble(R11_l[0]), Double.parseDouble(R11_l[1]), Double.parseDouble(R11_l[2]), Double.parseDouble(R11_l[3]), Double.parseDouble(R11_l[4]), Double.parseDouble(R11_l[5]),Double.parseDouble(R11_l[6])));
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
        Criteria cr = this.session.createCriteria(ModelR11Liquido.class);
        //cr = this.session.createCriteria(ModelR11Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R11 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR11Liquido> R11s = consulta.list(); 
        ModelR11Liquido R111 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Liquido R112 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Liquido R113 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Liquido R114 = R11s.get(0);
        
        double p  = ((pressao - R111.getPRESSAO())/(R113.getPRESSAO() - R111.getPRESSAO()));
        double t1 = ((temperatura - R111.getTEMPERATURA())/(R112.getTEMPERATURA() - R111.getTEMPERATURA()));
        double t2 = ((temperatura - R113.getTEMPERATURA())/(R114.getTEMPERATURA() - R113.getTEMPERATURA()));
        
        Cpl1 = R111.getCPL()+ (R112.getCPL()- R111.getCPL()) * t1;
        Cpl2 = R113.getCPL()+ (R114.getCPL()- R113.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R111.getPRL()+ (R112.getPRL()- R111.getPRL()) * t1;
        Prl2 = R113.getPRL()+ (R114.getPRL()- R113.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R111.getKL() + (R112.getKL() - R111.getKL()) * t1;
        kl2 = R113.getKL() + (R114.getKL() - R113.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R111.getMUL() + (R112.getMUL() - R111.getMUL()) * t1;
        Mul2 = R113.getMUL() + (R114.getMUL() - R113.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R111.getVCL() + (R112.getVCL() - R111.getVCL()) * t1;
        Vcl2 = R113.getVCL() + (R114.getVCL() - R113.getVCL()) * t2;
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
