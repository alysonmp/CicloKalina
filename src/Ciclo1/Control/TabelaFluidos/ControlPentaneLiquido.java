/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelPentaneLiquido;
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
public class ControlPentaneLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlPentaneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaPentaneLiquido(){
        String csvFile = "src/Csv/PENTANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelPentaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Pentane_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelPentaneLiquido(Double.parseDouble(Pentane_l[0]), Double.parseDouble(Pentane_l[1]), Double.parseDouble(Pentane_l[2]), Double.parseDouble(Pentane_l[3]), Double.parseDouble(Pentane_l[4]), Double.parseDouble(Pentane_l[5]),Double.parseDouble(Pentane_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelPentaneLiquido.class);
        //cr = this.session.createCriteria(ModelPentaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Pentane where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelPentaneLiquido> Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane1 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane2 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane3 = Pentanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Pentane where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPentaneLiquido.class));//Sem isso aqui impossível de retornar
	Pentanes = consulta.list(); 
        ModelPentaneLiquido Pentane4 = Pentanes.get(0);
        
        double p  = ((pressao - Pentane1.getPRESSAO())/(Pentane3.getPRESSAO() - Pentane1.getPRESSAO()));
        double t1 = ((temperatura - Pentane1.getTEMPERATURA())/(Pentane2.getTEMPERATURA() - Pentane1.getTEMPERATURA()));
        double t2 = ((temperatura - Pentane3.getTEMPERATURA())/(Pentane4.getTEMPERATURA() - Pentane3.getTEMPERATURA()));
        
        Cpl1 = Pentane1.getCPL()+ (Pentane2.getCPL()- Pentane1.getCPL()) * t1;
        Cpl2 = Pentane3.getCPL()+ (Pentane4.getCPL()- Pentane3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = Pentane1.getPRL()+ (Pentane2.getPRL()- Pentane1.getPRL()) * t1;
        Prl2 = Pentane3.getPRL()+ (Pentane4.getPRL()- Pentane3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = Pentane1.getKL() + (Pentane2.getKL() - Pentane1.getKL()) * t1;
        kl2 = Pentane3.getKL() + (Pentane4.getKL() - Pentane3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = Pentane1.getMUL() + (Pentane2.getMUL() - Pentane1.getMUL()) * t1;
        Mul2 = Pentane3.getMUL() + (Pentane4.getMUL() - Pentane3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = Pentane1.getVCL() + (Pentane2.getVCL() - Pentane1.getVCL()) * t1;
        Vcl2 = Pentane3.getVCL() + (Pentane4.getVCL() - Pentane3.getVCL()) * t2;
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
