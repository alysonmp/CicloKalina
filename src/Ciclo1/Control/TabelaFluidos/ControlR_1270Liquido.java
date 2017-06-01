/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR_1270Liquido;
import Ciclo1.Model.TabelasFluidos.ModelR_1270Liquido;
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
public class ControlR_1270Liquido {
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR_1270Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR_1270Liquido(){
        String csvFile = "src/Csv/R-1270_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {

            Criteria cr = this.session.createCriteria(ModelR_1270Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] r_1270_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR_1270Liquido(Double.parseDouble(r_1270_l[0]), Double.parseDouble(r_1270_l[1]), Double.parseDouble(r_1270_l[2]), Double.parseDouble(r_1270_l[3]), Double.parseDouble(r_1270_l[4]), Double.parseDouble(r_1270_l[5]),Double.parseDouble(r_1270_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR_1270Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from r_1270_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR_1270Liquido> r_1270_l = consulta.list(); 
        ModelR_1270Liquido r_1270_l1 = r_1270_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from r_1270_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Liquido.class));//Sem isso aqui impossível de retornar
	r_1270_l = consulta.list(); 
        ModelR_1270Liquido r_1270_l2 = r_1270_l.get(0);

        consulta = this.session.createSQLQuery("select * from r_1270_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Liquido.class));//Sem isso aqui impossível de retornar
	r_1270_l = consulta.list(); 
        ModelR_1270Liquido r_1270_l3 = r_1270_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from r_1270_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR_1270Liquido.class));//Sem isso aqui impossível de retornar
	r_1270_l = consulta.list(); 
        ModelR_1270Liquido r_1270_l4 = r_1270_l.get(0);
        
        
        
        double p  = ((pressao - r_1270_l1.getPRESSAO())/(r_1270_l3.getPRESSAO() - r_1270_l1.getPRESSAO()));
        double t1 = ((temperatura - r_1270_l1.getTEMPERATURA())/(r_1270_l2.getTEMPERATURA() - r_1270_l1.getTEMPERATURA()));
        double t2 = ((temperatura - r_1270_l3.getTEMPERATURA())/(r_1270_l4.getTEMPERATURA() - r_1270_l3.getTEMPERATURA()));
        
        Cpl1 = r_1270_l1.getCPL()+ (r_1270_l2.getCPL()- r_1270_l1.getCPL()) * t1;
        Cpl2 = r_1270_l3.getCPL()+ (r_1270_l4.getCPL()- r_1270_l3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = r_1270_l1.getPRL()+ (r_1270_l2.getPRL()- r_1270_l1.getPRL()) * t1;
        Prl2 = r_1270_l3.getPRL()+ (r_1270_l4.getPRL()- r_1270_l3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = r_1270_l1.getKL() + (r_1270_l2.getKL() - r_1270_l1.getKL()) * t1;
        kl2 = r_1270_l3.getKL() + (r_1270_l4.getKL() - r_1270_l3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = r_1270_l1.getMUL() + (r_1270_l2.getMUL() - r_1270_l1.getMUL()) * t1;
        Mul2 = r_1270_l3.getMUL() + (r_1270_l4.getMUL() - r_1270_l3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = r_1270_l1.getVCL() + (r_1270_l2.getVCL() - r_1270_l1.getVCL()) * t1;
        Vcl2 = r_1270_l3.getVCL() + (r_1270_l4.getVCL() - r_1270_l3.getVCL()) * t2;
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
