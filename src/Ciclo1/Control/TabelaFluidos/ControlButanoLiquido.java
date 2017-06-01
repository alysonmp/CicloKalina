/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.Ciclo1.ModelFluidos;
import Ciclo1.Model.TabelasFluidos.ModelButanoLiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author alysonmp
 */
public class ControlButanoLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlButanoLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaButanoLiquido(){
        String csvFile = "src/Csv/Butane_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {

            Criteria cr = this.session.createCriteria(ModelButanoLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] butano_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelButanoLiquido(Double.parseDouble(butano_l[0]), Double.parseDouble(butano_l[1]), Double.parseDouble(butano_l[2]), Double.parseDouble(butano_l[3]), Double.parseDouble(butano_l[4]), Double.parseDouble(butano_l[5]),Double.parseDouble(butano_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelButanoLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from butano_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelButanoLiquido> butano_l = consulta.list(); 
        ModelButanoLiquido butano_l1 = butano_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoLiquido.class));//Sem isso aqui impossível de retornar
	butano_l = consulta.list(); 
        ModelButanoLiquido butano_l2 = butano_l.get(0);

        consulta = this.session.createSQLQuery("select * from butano_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoLiquido.class));//Sem isso aqui impossível de retornar
	butano_l = consulta.list(); 
        ModelButanoLiquido butano_l3 = butano_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from butano_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelButanoLiquido.class));//Sem isso aqui impossível de retornar
	butano_l = consulta.list(); 
        ModelButanoLiquido butano_l4 = butano_l.get(0);
        
        double p  = ((pressao - butano_l1.getPRESSAO())/(butano_l3.getPRESSAO() - butano_l1.getPRESSAO()));
        double t1 = ((temperatura - butano_l1.getTEMPERATURA())/(butano_l2.getTEMPERATURA() - butano_l1.getTEMPERATURA()));
        double t2 = ((temperatura - butano_l3.getTEMPERATURA())/(butano_l4.getTEMPERATURA() - butano_l3.getTEMPERATURA()));
        
        Cpl1 = butano_l1.getCPL()+ (butano_l2.getCPL()- butano_l1.getCPL()) * t1;
        Cpl2 = butano_l3.getCPL()+ (butano_l4.getCPL()- butano_l3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = butano_l1.getPRL()+ (butano_l2.getPRL()- butano_l1.getPRL()) * t1;
        Prl2 = butano_l3.getPRL()+ (butano_l4.getPRL()- butano_l3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = butano_l1.getKL() + (butano_l2.getKL() - butano_l1.getKL()) * t1;
        kl2 = butano_l3.getKL() + (butano_l4.getKL() - butano_l3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = butano_l1.getMUL() + (butano_l2.getMUL() - butano_l1.getMUL()) * t1;
        Mul2 = butano_l3.getMUL() + (butano_l4.getMUL() - butano_l3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = butano_l1.getVCL() + (butano_l2.getVCL() - butano_l1.getVCL()) * t1;
        Vcl2 = butano_l3.getVCL() + (butano_l4.getVCL() - butano_l3.getVCL()) * t2;
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
