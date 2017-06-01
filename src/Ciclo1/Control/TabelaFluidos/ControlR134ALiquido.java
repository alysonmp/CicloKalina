/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR134ALiquido;
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
public class ControlR134ALiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR134ALiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR134ALiquido(){
        String csvFile = "src/Csv/R134A_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR134ALiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R134A_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR134ALiquido(Double.parseDouble(R134A_l[0]), Double.parseDouble(R134A_l[1]), Double.parseDouble(R134A_l[2]), Double.parseDouble(R134A_l[3]), Double.parseDouble(R134A_l[4]), Double.parseDouble(R134A_l[5]),Double.parseDouble(R134A_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR134ALiquido.class);
        //cr = this.session.createCriteria(ModelR134ALiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R134A where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR134ALiquido> R134As = consulta.list(); 
        ModelR134ALiquido R134A1 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134ALiquido R134A2 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134ALiquido R134A3 = R134As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R134A where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR134ALiquido.class));//Sem isso aqui impossível de retornar
	R134As = consulta.list(); 
        ModelR134ALiquido R134A4 = R134As.get(0);
        
        double p  = ((pressao - R134A1.getPRESSAO())/(R134A3.getPRESSAO() - R134A1.getPRESSAO()));
        double t1 = ((temperatura - R134A1.getTEMPERATURA())/(R134A2.getTEMPERATURA() - R134A1.getTEMPERATURA()));
        double t2 = ((temperatura - R134A3.getTEMPERATURA())/(R134A4.getTEMPERATURA() - R134A3.getTEMPERATURA()));
        
        Cpl1 = R134A1.getCPL()+ (R134A2.getCPL()- R134A1.getCPL()) * t1;
        Cpl2 = R134A3.getCPL()+ (R134A4.getCPL()- R134A3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R134A1.getPRL()+ (R134A2.getPRL()- R134A1.getPRL()) * t1;
        Prl2 = R134A3.getPRL()+ (R134A4.getPRL()- R134A3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R134A1.getKL() + (R134A2.getKL() - R134A1.getKL()) * t1;
        kl2 = R134A3.getKL() + (R134A4.getKL() - R134A3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R134A1.getMUL() + (R134A2.getMUL() - R134A1.getMUL()) * t1;
        Mul2 = R134A3.getMUL() + (R134A4.getMUL() - R134A3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R134A1.getVCL() + (R134A2.getVCL() - R134A1.getVCL()) * t1;
        Vcl2 = R134A3.getVCL() + (R134A4.getVCL() - R134A3.getVCL()) * t2;
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
