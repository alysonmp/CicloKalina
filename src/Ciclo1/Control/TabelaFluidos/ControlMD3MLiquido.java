/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelMD3MLiquido;
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
public class ControlMD3MLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlMD3MLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaMD3MLiquido(){
        String csvFile = "src/Csv/MD3M_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMD3MLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMD3Ma as separator
                    String[] MD3M_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelMD3MLiquido(Double.parseDouble(MD3M_l[0]), Double.parseDouble(MD3M_l[1]), Double.parseDouble(MD3M_l[2]), Double.parseDouble(MD3M_l[3]), Double.parseDouble(MD3M_l[4]), Double.parseDouble(MD3M_l[5]),Double.parseDouble(MD3M_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelMD3MLiquido.class);
        //cr = this.session.createCriteria(ModelMD3MLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MD3M where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMD3MLiquido> MD3Ms = consulta.list(); 
        ModelMD3MLiquido MD3M1 = MD3Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD3M where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MLiquido.class));//Sem isso aqui impossível de retornar
	MD3Ms = consulta.list(); 
        ModelMD3MLiquido MD3M2 = MD3Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD3M where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MLiquido.class));//Sem isso aqui impossível de retornar
	MD3Ms = consulta.list(); 
        ModelMD3MLiquido MD3M3 = MD3Ms.get(0);
        
        consulta = this.session.createSQLQuery("select * from MD3M where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD3MLiquido.class));//Sem isso aqui impossível de retornar
	MD3Ms = consulta.list(); 
        ModelMD3MLiquido MD3M4 = MD3Ms.get(0);
        
        double p  = ((pressao - MD3M1.getPRESSAO())/(MD3M3.getPRESSAO() - MD3M1.getPRESSAO()));
        double t1 = ((temperatura - MD3M1.getTEMPERATURA())/(MD3M2.getTEMPERATURA() - MD3M1.getTEMPERATURA()));
        double t2 = ((temperatura - MD3M3.getTEMPERATURA())/(MD3M4.getTEMPERATURA() - MD3M3.getTEMPERATURA()));
        
        Cpl1 = MD3M1.getCPL()+ (MD3M2.getCPL()- MD3M1.getCPL()) * t1;
        Cpl2 = MD3M3.getCPL()+ (MD3M4.getCPL()- MD3M3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = MD3M1.getPRL()+ (MD3M2.getPRL()- MD3M1.getPRL()) * t1;
        Prl2 = MD3M3.getPRL()+ (MD3M4.getPRL()- MD3M3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = MD3M1.getKL() + (MD3M2.getKL() - MD3M1.getKL()) * t1;
        kl2 = MD3M3.getKL() + (MD3M4.getKL() - MD3M3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = MD3M1.getMUL() + (MD3M2.getMUL() - MD3M1.getMUL()) * t1;
        Mul2 = MD3M3.getMUL() + (MD3M4.getMUL() - MD3M3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = MD3M1.getVCL() + (MD3M2.getVCL() - MD3M1.getVCL()) * t1;
        Vcl2 = MD3M3.getVCL() + (MD3M4.getVCL() - MD3M3.getVCL()) * t2;
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
