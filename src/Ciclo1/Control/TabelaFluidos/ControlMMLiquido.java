/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelMMLiquido;
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
public class ControlMMLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlMMLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaMMLiquido(){
        String csvFile = "src/Csv/MM_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMMLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] MM_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelMMLiquido(Double.parseDouble(MM_l[0]), Double.parseDouble(MM_l[1]), Double.parseDouble(MM_l[2]), Double.parseDouble(MM_l[3]), Double.parseDouble(MM_l[4]), Double.parseDouble(MM_l[5]),Double.parseDouble(MM_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelMMLiquido.class);
        //cr = this.session.createCriteria(ModelMMLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MM where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMMLiquido> MMs = consulta.list(); 
        ModelMMLiquido MM1 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMLiquido MM2 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMLiquido MM3 = MMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MM where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMMLiquido.class));//Sem isso aqui impossível de retornar
	MMs = consulta.list(); 
        ModelMMLiquido MM4 = MMs.get(0);
        
        double p  = ((pressao - MM1.getPRESSAO())/(MM3.getPRESSAO() - MM1.getPRESSAO()));
        double t1 = ((temperatura - MM1.getTEMPERATURA())/(MM2.getTEMPERATURA() - MM1.getTEMPERATURA()));
        double t2 = ((temperatura - MM3.getTEMPERATURA())/(MM4.getTEMPERATURA() - MM3.getTEMPERATURA()));
        
        Cpl1 = MM1.getCPL()+ (MM2.getCPL()- MM1.getCPL()) * t1;
        Cpl2 = MM3.getCPL()+ (MM4.getCPL()- MM3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = MM1.getPRL()+ (MM2.getPRL()- MM1.getPRL()) * t1;
        Prl2 = MM3.getPRL()+ (MM4.getPRL()- MM3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = MM1.getKL() + (MM2.getKL() - MM1.getKL()) * t1;
        kl2 = MM3.getKL() + (MM4.getKL() - MM3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = MM1.getMUL() + (MM2.getMUL() - MM1.getMUL()) * t1;
        Mul2 = MM3.getMUL() + (MM4.getMUL() - MM3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = MM1.getVCL() + (MM2.getVCL() - MM1.getVCL()) * t1;
        Vcl2 = MM3.getVCL() + (MM4.getVCL() - MM3.getVCL()) * t2;
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
