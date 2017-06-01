/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelPropylbenzeneLiquido;
import Ciclo1.Model.TabelasFluidos.ModelPropylbenzeneLiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author leonardo
 */
public class ControlPropylbenzeneLiquido {
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlPropylbenzeneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaPropylbenzeneLiquido(){
        String csvFile = "src/Csv/Propylbenzene_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {

            Criteria cr = this.session.createCriteria(ModelPropylbenzeneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] propylbenze_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelPropylbenzeneLiquido(Double.parseDouble(propylbenze_l[0]), Double.parseDouble(propylbenze_l[1]), Double.parseDouble(propylbenze_l[2]), Double.parseDouble(propylbenze_l[3]), Double.parseDouble(propylbenze_l[4]), Double.parseDouble(propylbenze_l[5]),Double.parseDouble(propylbenze_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelPropylbenzeneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from propylbenzene_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelPropylbenzeneLiquido> propylbenze_l = consulta.list(); 
        ModelPropylbenzeneLiquido propylbenze_l1 = propylbenze_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from propylbenzene_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	propylbenze_l = consulta.list(); 
        ModelPropylbenzeneLiquido propylbenze_l2 = propylbenze_l.get(0);

        consulta = this.session.createSQLQuery("select * from propylbenzene_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	propylbenze_l = consulta.list(); 
        ModelPropylbenzeneLiquido propylbenze_l3 = propylbenze_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from propylbenzene_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelPropylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	propylbenze_l = consulta.list(); 
        ModelPropylbenzeneLiquido propylbenze_l4 = propylbenze_l.get(0);
        
        double p  = ((pressao - propylbenze_l1.getPRESSAO())/(propylbenze_l3.getPRESSAO() - propylbenze_l1.getPRESSAO()));
        double t1 = ((temperatura - propylbenze_l1.getTEMPERATURA())/(propylbenze_l2.getTEMPERATURA() - propylbenze_l1.getTEMPERATURA()));
        double t2 = ((temperatura - propylbenze_l3.getTEMPERATURA())/(propylbenze_l4.getTEMPERATURA() - propylbenze_l3.getTEMPERATURA()));
        
        Cpl1 = propylbenze_l1.getCPL()+ (propylbenze_l2.getCPL()- propylbenze_l1.getCPL()) * t1;
        Cpl2 = propylbenze_l3.getCPL()+ (propylbenze_l4.getCPL()- propylbenze_l3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = propylbenze_l1.getPRL()+ (propylbenze_l2.getPRL()- propylbenze_l1.getPRL()) * t1;
        Prl2 = propylbenze_l3.getPRL()+ (propylbenze_l4.getPRL()- propylbenze_l3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = propylbenze_l1.getKL() + (propylbenze_l2.getKL() - propylbenze_l1.getKL()) * t1;
        kl2 = propylbenze_l3.getKL() + (propylbenze_l4.getKL() - propylbenze_l3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = propylbenze_l1.getMUL() + (propylbenze_l2.getMUL() - propylbenze_l1.getMUL()) * t1;
        Mul2 = propylbenze_l3.getMUL() + (propylbenze_l4.getMUL() - propylbenze_l3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = propylbenze_l1.getVCL() + (propylbenze_l2.getVCL() - propylbenze_l1.getVCL()) * t1;
        Vcl2 = propylbenze_l3.getVCL() + (propylbenze_l4.getVCL() - propylbenze_l3.getVCL()) * t2;
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
