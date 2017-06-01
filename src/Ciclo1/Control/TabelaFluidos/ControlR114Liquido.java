/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR114Liquido;
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
public class ControlR114Liquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR114Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR114Liquido(){
        String csvFile = "src/Csv/R114_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR114Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R114_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR114Liquido(Double.parseDouble(R114_l[0]), Double.parseDouble(R114_l[1]), Double.parseDouble(R114_l[2]), Double.parseDouble(R114_l[3]), Double.parseDouble(R114_l[4]), Double.parseDouble(R114_l[5]),Double.parseDouble(R114_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR114Liquido.class);
        //cr = this.session.createCriteria(ModelR114Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R114 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR114Liquido> R114s = consulta.list(); 
        ModelR114Liquido R1141 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Liquido R1142 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Liquido R1143 = R114s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R114 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR114Liquido.class));//Sem isso aqui impossível de retornar
	R114s = consulta.list(); 
        ModelR114Liquido R1144 = R114s.get(0);
        
        double p  = ((pressao - R1141.getPRESSAO())/(R1143.getPRESSAO() - R1141.getPRESSAO()));
        double t1 = ((temperatura - R1141.getTEMPERATURA())/(R1142.getTEMPERATURA() - R1141.getTEMPERATURA()));
        double t2 = ((temperatura - R1143.getTEMPERATURA())/(R1144.getTEMPERATURA() - R1143.getTEMPERATURA()));
        
        Cpl1 = R1141.getCPL()+ (R1142.getCPL()- R1141.getCPL()) * t1;
        Cpl2 = R1143.getCPL()+ (R1144.getCPL()- R1143.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R1141.getPRL()+ (R1142.getPRL()- R1141.getPRL()) * t1;
        Prl2 = R1143.getPRL()+ (R1144.getPRL()- R1143.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R1141.getKL() + (R1142.getKL() - R1141.getKL()) * t1;
        kl2 = R1143.getKL() + (R1144.getKL() - R1143.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R1141.getMUL() + (R1142.getMUL() - R1141.getMUL()) * t1;
        Mul2 = R1143.getMUL() + (R1144.getMUL() - R1143.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R1141.getVCL() + (R1142.getVCL() - R1141.getVCL()) * t1;
        Vcl2 = R1143.getVCL() + (R1144.getVCL() - R1143.getVCL()) * t2;
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
