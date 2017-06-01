/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelMD4MLiquido;
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
public class ControlMD4MLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlMD4MLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaMD4MLiquido(){
        String csvFile = "src/Csv/MD4M_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMD4MLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMD4Ma as separator
                    String[] MD4M_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelMD4MLiquido(Double.parseDouble(MD4M_l[0]), Double.parseDouble(MD4M_l[1]), Double.parseDouble(MD4M_l[2]), Double.parseDouble(MD4M_l[3]), Double.parseDouble(MD4M_l[4]), Double.parseDouble(MD4M_l[5]),Double.parseDouble(MD4M_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelMD4MLiquido.class);
        //cr = this.session.createCriteria(ModelMD4MLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MD4M where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMD4MLiquido> MD4Ms = consulta.list(); 
        ModelMD4MLiquido MD4M1 = MD4Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD4M where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MLiquido.class));//Sem isso aqui impossível de retornar
	MD4Ms = consulta.list(); 
        ModelMD4MLiquido MD4M2 = MD4Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD4M where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MLiquido.class));//Sem isso aqui impossível de retornar
	MD4Ms = consulta.list(); 
        ModelMD4MLiquido MD4M3 = MD4Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD4M where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD4MLiquido.class));//Sem isso aqui impossível de retornar
	MD4Ms = consulta.list(); 
        ModelMD4MLiquido MD4M4 = MD4Ms.get(0);
        
        double p  = ((pressao - MD4M1.getPRESSAO())/(MD4M3.getPRESSAO() - MD4M1.getPRESSAO()));
        double t1 = ((temperatura - MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA() - MD4M1.getTEMPERATURA()));
        double t2 = ((temperatura - MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA() - MD4M3.getTEMPERATURA()));
        
        Cpl1 = MD4M1.getCPL()+ (MD4M2.getCPL()- MD4M1.getCPL()) * t1;
        Cpl2 = MD4M3.getCPL()+ (MD4M4.getCPL()- MD4M3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = MD4M1.getPRL()+ (MD4M2.getPRL()- MD4M1.getPRL()) * t1;
        Prl2 = MD4M3.getPRL()+ (MD4M4.getPRL()- MD4M3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = MD4M1.getKL() + (MD4M2.getKL() - MD4M1.getKL()) * t1;
        kl2 = MD4M3.getKL() + (MD4M4.getKL() - MD4M3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = MD4M1.getMUL() + (MD4M2.getMUL() - MD4M1.getMUL()) * t1;
        Mul2 = MD4M3.getMUL() + (MD4M4.getMUL() - MD4M3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = MD4M1.getVCL() + (MD4M2.getVCL() - MD4M1.getVCL()) * t1;
        Vcl2 = MD4M3.getVCL() + (MD4M4.getVCL() - MD4M3.getVCL()) * t2;
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
