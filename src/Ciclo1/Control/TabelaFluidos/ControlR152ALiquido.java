/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR152ALiquido;
import Ciclo1.Model.TabelasFluidos.ModelR152ALiquido;
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
public class ControlR152ALiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR152ALiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR152ALiquido(){
        String csvFile = "src/Csv/R152A_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR152ALiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R152A_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR152ALiquido(Double.parseDouble(R152A_l[0]), Double.parseDouble(R152A_l[1]), Double.parseDouble(R152A_l[2]), Double.parseDouble(R152A_l[3]), Double.parseDouble(R152A_l[4]), Double.parseDouble(R152A_l[5]),Double.parseDouble(R152A_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR152ALiquido.class);
        //cr = this.session.createCriteria(ModelR152ALiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R152A where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR152ALiquido> R152As = consulta.list(); 
        ModelR152ALiquido R152A1 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152ALiquido R152A2 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152ALiquido R152A3 = R152As.get(0);
        
        consulta = this.session.createSQLQuery("select * from R152A where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR152ALiquido.class));//Sem isso aqui impossível de retornar
	R152As = consulta.list(); 
        ModelR152ALiquido R152A4 = R152As.get(0);
        
        double p  = ((pressao - R152A1.getPRESSAO())/(R152A3.getPRESSAO() - R152A1.getPRESSAO()));
        double t1 = ((temperatura - R152A1.getTEMPERATURA())/(R152A2.getTEMPERATURA() - R152A1.getTEMPERATURA()));
        double t2 = ((temperatura - R152A3.getTEMPERATURA())/(R152A4.getTEMPERATURA() - R152A3.getTEMPERATURA()));
        
        Cpl1 = R152A1.getCPL()+ (R152A2.getCPL()- R152A1.getCPL()) * t1;
        Cpl2 = R152A3.getCPL()+ (R152A4.getCPL()- R152A3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = R152A1.getPRL()+ (R152A2.getPRL()- R152A1.getPRL()) * t1;
        Prl2 = R152A3.getPRL()+ (R152A4.getPRL()- R152A3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = R152A1.getKL() + (R152A2.getKL() - R152A1.getKL()) * t1;
        kl2 = R152A3.getKL() + (R152A4.getKL() - R152A3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = R152A1.getMUL() + (R152A2.getMUL() - R152A1.getMUL()) * t1;
        Mul2 = R152A3.getMUL() + (R152A4.getMUL() - R152A3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = R152A1.getVCL() + (R152A2.getVCL() - R152A1.getVCL()) * t1;
        Vcl2 = R152A3.getVCL() + (R152A4.getVCL() - R152A3.getVCL()) * t2;
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
