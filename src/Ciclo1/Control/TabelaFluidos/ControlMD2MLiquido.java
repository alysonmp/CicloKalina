/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelMD2MLiquido;
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
 * @author leonardo
 */
public class ControlMD2MLiquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;

    public ControlMD2MLiquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaMD2MLiquido(){
        String csvFile = "src/Csv/MD2M_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelMD2MLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] md2m_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelMD2MLiquido(Double.parseDouble(md2m_liquido[0]), Double.parseDouble(md2m_liquido[1]), Double.parseDouble(md2m_liquido[2]), Double.parseDouble(md2m_liquido[3]), Double.parseDouble(md2m_liquido[4]), Double.parseDouble(md2m_liquido[5]),Double.parseDouble(md2m_liquido[6])));
                }
            }
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
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
        Criteria cr = this.session.createCriteria(ModelMD2MLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from md2m_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD2MLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMD2MLiquido> md2m_liquido = consulta.list(); 
        ModelMD2MLiquido md2m_liquido1 = md2m_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from md2m_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD2MLiquido.class));//Sem isso aqui impossível de retornar
	md2m_liquido = consulta.list(); 
        ModelMD2MLiquido md2m_liquido2 = md2m_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from md2m_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD2MLiquido.class));//Sem isso aqui impossível de retornar
	md2m_liquido = consulta.list(); 
        ModelMD2MLiquido md2m_liquido3 = md2m_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from md2m_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMD2MLiquido.class));//Sem isso aqui impossível de retornar
	md2m_liquido = consulta.list(); 
        ModelMD2MLiquido md2m_liquido4 = md2m_liquido.get(0);
     
        double p  = ((pressao - md2m_liquido1.getPRESSAO())/(md2m_liquido3.getPRESSAO() - md2m_liquido1.getPRESSAO()));
        double t1 = ((temperatura - md2m_liquido1.getTEMPERATURA())/(md2m_liquido2.getTEMPERATURA() - md2m_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - md2m_liquido3.getTEMPERATURA())/(md2m_liquido4.getTEMPERATURA() - md2m_liquido3.getTEMPERATURA()));
        
        Cpl1 = md2m_liquido1.getCPL()+ (md2m_liquido2.getCPL()- md2m_liquido1.getCPL()) * t1;
        Cpl2 = md2m_liquido3.getCPL()+ (md2m_liquido4.getCPL()- md2m_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = md2m_liquido1.getPRL()+ (md2m_liquido2.getPRL()- md2m_liquido1.getPRL()) * t1;
        Prl2 = md2m_liquido3.getPRL()+ (md2m_liquido4.getPRL()- md2m_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = md2m_liquido1.getKL() + (md2m_liquido2.getKL() - md2m_liquido1.getKL()) * t1;
        kl2 = md2m_liquido3.getKL() + (md2m_liquido4.getKL() - md2m_liquido3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = md2m_liquido1.getMUL() + (md2m_liquido2.getMUL() - md2m_liquido1.getMUL()) * t1;
        Mul2 = md2m_liquido3.getMUL() + (md2m_liquido4.getMUL() - md2m_liquido3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = md2m_liquido1.getVCL() + (md2m_liquido2.getVCL() - md2m_liquido1.getVCL()) * t1;
        Vcl2 = md2m_liquido3.getVCL() + (md2m_liquido4.getVCL() - md2m_liquido3.getVCL()) * t2;
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
