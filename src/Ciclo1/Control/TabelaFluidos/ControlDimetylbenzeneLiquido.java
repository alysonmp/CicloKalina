/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelDimetylbenzeneLiquido;
import Ciclo1.Model.TabelasFluidos.ModelDimetylbenzeneLiquido;
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
public class ControlDimetylbenzeneLiquido {
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlDimetylbenzeneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaDimethylbenzeneLiquido(){
        String csvFile = "src/Csv/Dimethylbenzene_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {

            Criteria cr = this.session.createCriteria(ModelDimetylbenzeneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] dimetylbenzene_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelDimetylbenzeneLiquido(Double.parseDouble(dimetylbenzene_l[0]), Double.parseDouble(dimetylbenzene_l[1]), Double.parseDouble(dimetylbenzene_l[2]), Double.parseDouble(dimetylbenzene_l[3]), Double.parseDouble(dimetylbenzene_l[4]), Double.parseDouble(dimetylbenzene_l[5]),Double.parseDouble(dimetylbenzene_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelDimetylbenzeneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from dimetylbenzene_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelDimetylbenzeneLiquido> dimetylbenzene_l = consulta.list(); 
        ModelDimetylbenzeneLiquido dimetylbenzene_l1 = dimetylbenzene_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from dimetylbenzene_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	dimetylbenzene_l = consulta.list(); 
        ModelDimetylbenzeneLiquido dimetylbenzene_l2 = dimetylbenzene_l.get(0);

        consulta = this.session.createSQLQuery("select * from dimetylbenzene_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	dimetylbenzene_l = consulta.list(); 
        ModelDimetylbenzeneLiquido dimetylbenzene_l3 = dimetylbenzene_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from dimetylbenzene_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelDimetylbenzeneLiquido.class));//Sem isso aqui impossível de retornar
	dimetylbenzene_l = consulta.list(); 
        ModelDimetylbenzeneLiquido dimetylbenzene_l4 = dimetylbenzene_l.get(0);
        
        double p  = ((pressao - dimetylbenzene_l1.getPRESSAO())/(dimetylbenzene_l3.getPRESSAO() - dimetylbenzene_l1.getPRESSAO()));
        double t1 = ((temperatura - dimetylbenzene_l1.getTEMPERATURA())/(dimetylbenzene_l2.getTEMPERATURA() - dimetylbenzene_l1.getTEMPERATURA()));
        double t2 = ((temperatura - dimetylbenzene_l3.getTEMPERATURA())/(dimetylbenzene_l4.getTEMPERATURA() - dimetylbenzene_l3.getTEMPERATURA()));
        
        Cpl1 = dimetylbenzene_l1.getCPL()+ (dimetylbenzene_l2.getCPL()- dimetylbenzene_l1.getCPL()) * t1;
        Cpl2 = dimetylbenzene_l3.getCPL()+ (dimetylbenzene_l4.getCPL()- dimetylbenzene_l3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = dimetylbenzene_l1.getPRL()+ (dimetylbenzene_l2.getPRL()- dimetylbenzene_l1.getPRL()) * t1;
        Prl2 = dimetylbenzene_l3.getPRL()+ (dimetylbenzene_l4.getPRL()- dimetylbenzene_l3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = dimetylbenzene_l1.getKL() + (dimetylbenzene_l2.getKL() - dimetylbenzene_l1.getKL()) * t1;
        kl2 = dimetylbenzene_l3.getKL() + (dimetylbenzene_l4.getKL() - dimetylbenzene_l3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = dimetylbenzene_l1.getMUL() + (dimetylbenzene_l2.getMUL() - dimetylbenzene_l1.getMUL()) * t1;
        Mul2 = dimetylbenzene_l3.getMUL() + (dimetylbenzene_l4.getMUL() - dimetylbenzene_l3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = dimetylbenzene_l1.getVCL() + (dimetylbenzene_l2.getVCL() - dimetylbenzene_l1.getVCL()) * t1;
        Vcl2 = dimetylbenzene_l3.getVCL() + (dimetylbenzene_l4.getVCL() - dimetylbenzene_l3.getVCL()) * t2;
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
