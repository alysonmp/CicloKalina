/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR22Liquido;
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
public class ControlR22Liquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR22Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR22Liquido(){
        String csvFile = "src/Csv/R22_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR22Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R22_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR22Liquido(Double.parseDouble(R22_l[0]), Double.parseDouble(R22_l[1]), Double.parseDouble(R22_l[2]), Double.parseDouble(R22_l[3]), Double.parseDouble(R22_l[4]), Double.parseDouble(R22_l[5]),Double.parseDouble(R22_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR22Liquido.class);
        //cr = this.session.createCriteria(ModelR22Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R22 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR22Liquido> R22s = consulta.list(); 
        ModelR22Liquido R221 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Liquido R222 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Liquido R223 = R22s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R22 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR22Liquido.class));//Sem isso aqui impossível de retornar
	R22s = consulta.list(); 
        ModelR22Liquido R224 = R22s.get(0);
        
        double p  = ((pressao - R221.getPRESSAO())/(R223.getPRESSAO() - R221.getPRESSAO()));
        double t1 = ((temperatura - R221.getTEMPERATURA())/(R222.getTEMPERATURA() - R221.getTEMPERATURA()));
        double t2 = ((temperatura - R223.getTEMPERATURA())/(R224.getTEMPERATURA() - R223.getTEMPERATURA()));
        
        Cpl1 = R221.getCPL()+ (R222.getCPL()- R221.getCPL()) * t1;
        Cpl2 = R223.getCPL()+ (R224.getCPL()- R223.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R221.getPRL()+ (R222.getPRL()- R221.getPRL()) * t1;
        Prl2 = R223.getPRL()+ (R224.getPRL()- R223.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R221.getKL() + (R222.getKL() - R221.getKL()) * t1;
        kl2 = R223.getKL() + (R224.getKL() - R223.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R221.getMUL() + (R222.getMUL() - R221.getMUL()) * t1;
        Mul2 = R223.getMUL() + (R224.getMUL() - R223.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R221.getVCL() + (R222.getVCL() - R221.getVCL()) * t1;
        Vcl2 = R223.getVCL() + (R224.getVCL() - R223.getVCL()) * t2;
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
