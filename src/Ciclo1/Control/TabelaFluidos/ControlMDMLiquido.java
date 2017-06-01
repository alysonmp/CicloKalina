/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelMDMLiquido;
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
public class ControlMDMLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlMDMLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaMDMLiquido(){
        String csvFile = "src/Csv/MDM_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMDMLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMDMa as separator
                    String[] MDM_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelMDMLiquido(Double.parseDouble(MDM_l[0]), Double.parseDouble(MDM_l[1]), Double.parseDouble(MDM_l[2]), Double.parseDouble(MDM_l[3]), Double.parseDouble(MDM_l[4]), Double.parseDouble(MDM_l[5]),Double.parseDouble(MDM_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelMDMLiquido.class);
        //cr = this.session.createCriteria(ModelMDMLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MDM where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMDMLiquido> MDMs = consulta.list(); 
        ModelMDMLiquido MDM1 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMLiquido MDM2 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMLiquido MDM3 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMLiquido MDM4 = MDMs.get(0);
        
        double p  = ((pressao - MDM1.getPRESSAO())/(MDM3.getPRESSAO() - MDM1.getPRESSAO()));
        double t1 = ((temperatura - MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA() - MDM1.getTEMPERATURA()));
        double t2 = ((temperatura - MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA() - MDM3.getTEMPERATURA()));
        
        Cpl1 = MDM1.getCPL()+ (MDM2.getCPL()- MDM1.getCPL()) * t1;
        Cpl2 = MDM3.getCPL()+ (MDM4.getCPL()- MDM3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = MDM1.getPRL()+ (MDM2.getPRL()- MDM1.getPRL()) * t1;
        Prl2 = MDM3.getPRL()+ (MDM4.getPRL()- MDM3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = MDM1.getKL() + (MDM2.getKL() - MDM1.getKL()) * t1;
        kl2 = MDM3.getKL() + (MDM4.getKL() - MDM3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = MDM1.getMUL() + (MDM2.getMUL() - MDM1.getMUL()) * t1;
        Mul2 = MDM3.getMUL() + (MDM4.getMUL() - MDM3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = MDM1.getVCL() + (MDM2.getVCL() - MDM1.getVCL()) * t1;
        Vcl2 = MDM3.getVCL() + (MDM4.getVCL() - MDM3.getVCL()) * t2;
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
