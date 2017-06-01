/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR12Liquido;
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
public class ControlR12Liquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR12Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR12Liquido(){
        String csvFile = "src/Csv/R12_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR12Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R12_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR12Liquido(Double.parseDouble(R12_l[0]), Double.parseDouble(R12_l[1]), Double.parseDouble(R12_l[2]), Double.parseDouble(R12_l[3]), Double.parseDouble(R12_l[4]), Double.parseDouble(R12_l[5]),Double.parseDouble(R12_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR12Liquido.class);
        //cr = this.session.createCriteria(ModelR12Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R12 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR12Liquido> R12s = consulta.list(); 
        ModelR12Liquido R121 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Liquido R122 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Liquido R123 = R12s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R12 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR12Liquido.class));//Sem isso aqui impossível de retornar
	R12s = consulta.list(); 
        ModelR12Liquido R124 = R12s.get(0);
        
        double p  = ((pressao - R121.getPRESSAO())/(R123.getPRESSAO() - R121.getPRESSAO()));
        double t1 = ((temperatura - R121.getTEMPERATURA())/(R122.getTEMPERATURA() - R121.getTEMPERATURA()));
        double t2 = ((temperatura - R123.getTEMPERATURA())/(R124.getTEMPERATURA() - R123.getTEMPERATURA()));
        
        Cpl1 = R121.getCPL()+ (R122.getCPL()- R121.getCPL()) * t1;
        Cpl2 = R123.getCPL()+ (R124.getCPL()- R123.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R121.getPRL()+ (R122.getPRL()- R121.getPRL()) * t1;
        Prl2 = R123.getPRL()+ (R124.getPRL()- R123.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R121.getKL() + (R122.getKL() - R121.getKL()) * t1;
        kl2 = R123.getKL() + (R124.getKL() - R123.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R121.getMUL() + (R122.getMUL() - R121.getMUL()) * t1;
        Mul2 = R123.getMUL() + (R124.getMUL() - R123.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R121.getVCL() + (R122.getVCL() - R121.getVCL()) * t1;
        Vcl2 = R123.getVCL() + (R124.getVCL() - R123.getVCL()) * t2;
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
