/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR142BLiquido;
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
public class ControlR142BLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR142BLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR142BLiquido(){
        String csvFile = "src/Csv/R142B_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR142BLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R142B_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR142BLiquido(Double.parseDouble(R142B_l[0]), Double.parseDouble(R142B_l[1]), Double.parseDouble(R142B_l[2]), Double.parseDouble(R142B_l[3]), Double.parseDouble(R142B_l[4]), Double.parseDouble(R142B_l[5]),Double.parseDouble(R142B_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR142BLiquido.class);
        //cr = this.session.createCriteria(ModelR142BLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R142B where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR142BLiquido> R142Bs = consulta.list(); 
        ModelR142BLiquido R142B1 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BLiquido R142B2 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BLiquido R142B3 = R142Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R142B where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR142BLiquido.class));//Sem isso aqui impossível de retornar
	R142Bs = consulta.list(); 
        ModelR142BLiquido R142B4 = R142Bs.get(0);
        
        double p  = ((pressao - R142B1.getPRESSAO())/(R142B3.getPRESSAO() - R142B1.getPRESSAO()));
        double t1 = ((temperatura - R142B1.getTEMPERATURA())/(R142B2.getTEMPERATURA() - R142B1.getTEMPERATURA()));
        double t2 = ((temperatura - R142B3.getTEMPERATURA())/(R142B4.getTEMPERATURA() - R142B3.getTEMPERATURA()));
        
        Cpl1 = R142B1.getCPL()+ (R142B2.getCPL()- R142B1.getCPL()) * t1;
        Cpl2 = R142B3.getCPL()+ (R142B4.getCPL()- R142B3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R142B1.getPRL()+ (R142B2.getPRL()- R142B1.getPRL()) * t1;
        Prl2 = R142B3.getPRL()+ (R142B4.getPRL()- R142B3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R142B1.getKL() + (R142B2.getKL() - R142B1.getKL()) * t1;
        kl2 = R142B3.getKL() + (R142B4.getKL() - R142B3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R142B1.getMUL() + (R142B2.getMUL() - R142B1.getMUL()) * t1;
        Mul2 = R142B3.getMUL() + (R142B4.getMUL() - R142B3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R142B1.getVCL() + (R142B2.getVCL() - R142B1.getVCL()) * t1;
        Vcl2 = R142B3.getVCL() + (R142B4.getVCL() - R142B3.getVCL()) * t2;
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
