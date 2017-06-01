/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelPropaneLiquido;
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
public class ControlPropaneLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlPropaneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaPropaneLiquido(){
        String csvFile = "src/Csv/PROPANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelPropaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Propane_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelPropaneLiquido(Double.parseDouble(Propane_l[0]), Double.parseDouble(Propane_l[1]), Double.parseDouble(Propane_l[2]), Double.parseDouble(Propane_l[3]), Double.parseDouble(Propane_l[4]), Double.parseDouble(Propane_l[5]),Double.parseDouble(Propane_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelPropaneLiquido.class);
        //cr = this.session.createCriteria(ModelPropaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Propane where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelPropaneLiquido> Propanes = consulta.list(); 
        ModelPropaneLiquido Propane1 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneLiquido Propane2 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneLiquido Propane3 = Propanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Propane where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropaneLiquido.class));//Sem isso aqui impossível de retornar
	Propanes = consulta.list(); 
        ModelPropaneLiquido Propane4 = Propanes.get(0);
        
        double p  = ((pressao - Propane1.getPRESSAO())/(Propane3.getPRESSAO() - Propane1.getPRESSAO()));
        double t1 = ((temperatura - Propane1.getTEMPERATURA())/(Propane2.getTEMPERATURA() - Propane1.getTEMPERATURA()));
        double t2 = ((temperatura - Propane3.getTEMPERATURA())/(Propane4.getTEMPERATURA() - Propane3.getTEMPERATURA()));
        
        Cpl1 = Propane1.getCPL()+ (Propane2.getCPL()- Propane1.getCPL()) * t1;
        Cpl2 = Propane3.getCPL()+ (Propane4.getCPL()- Propane3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = Propane1.getPRL()+ (Propane2.getPRL()- Propane1.getPRL()) * t1;
        Prl2 = Propane3.getPRL()+ (Propane4.getPRL()- Propane3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = Propane1.getKL() + (Propane2.getKL() - Propane1.getKL()) * t1;
        kl2 = Propane3.getKL() + (Propane4.getKL() - Propane3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = Propane1.getMUL() + (Propane2.getMUL() - Propane1.getMUL()) * t1;
        Mul2 = Propane3.getMUL() + (Propane4.getMUL() - Propane3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = Propane1.getVCL() + (Propane2.getVCL() - Propane1.getVCL()) * t1;
        Vcl2 = Propane3.getVCL() + (Propane4.getVCL() - Propane3.getVCL()) * t2;
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
