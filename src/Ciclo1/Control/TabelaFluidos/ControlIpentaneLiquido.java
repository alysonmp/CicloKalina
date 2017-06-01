/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelIpentaneLiquido;
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
public class ControlIpentaneLiquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;

    public ControlIpentaneLiquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaIpentaneLiquido(){
        String csvFile = "src/Csv/IPENTANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelIpentaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] ipentane_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelIpentaneLiquido(Double.parseDouble(ipentane_liquido[0]), Double.parseDouble(ipentane_liquido[1]), Double.parseDouble(ipentane_liquido[2]), Double.parseDouble(ipentane_liquido[3]), Double.parseDouble(ipentane_liquido[4]), Double.parseDouble(ipentane_liquido[5]),Double.parseDouble(ipentane_liquido[6])));
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
        Criteria cr = this.session.createCriteria(ModelIpentaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelIpentaneLiquido> ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido1 = ipentane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido2 = ipentane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido3 = ipentane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from ipentane_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelIpentaneLiquido.class));//Sem isso aqui impossível de retornar
	ipentane_liquido = consulta.list(); 
        ModelIpentaneLiquido ipentane_liquido4 = ipentane_liquido.get(0);
     
        double p  = ((pressao - ipentane_liquido1.getPRESSAO())/(ipentane_liquido3.getPRESSAO() - ipentane_liquido1.getPRESSAO()));
        double t1 = ((temperatura - ipentane_liquido1.getTEMPERATURA())/(ipentane_liquido2.getTEMPERATURA() - ipentane_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - ipentane_liquido3.getTEMPERATURA())/(ipentane_liquido4.getTEMPERATURA() - ipentane_liquido3.getTEMPERATURA()));
        
        Cpl1 = ipentane_liquido1.getCPL()+ (ipentane_liquido2.getCPL()- ipentane_liquido1.getCPL()) * t1;
        Cpl2 = ipentane_liquido3.getCPL()+ (ipentane_liquido4.getCPL()- ipentane_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = ipentane_liquido1.getPRL()+ (ipentane_liquido2.getPRL()- ipentane_liquido1.getPRL()) * t1;
        Prl2 = ipentane_liquido3.getPRL()+ (ipentane_liquido4.getPRL()- ipentane_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = ipentane_liquido1.getKL() + (ipentane_liquido2.getKL() - ipentane_liquido1.getKL()) * t1;
        kl2 = ipentane_liquido3.getKL() + (ipentane_liquido4.getKL() - ipentane_liquido3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = ipentane_liquido1.getMUL() + (ipentane_liquido2.getMUL() - ipentane_liquido1.getMUL()) * t1;
        Mul2 = ipentane_liquido3.getMUL() + (ipentane_liquido4.getMUL() - ipentane_liquido3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = ipentane_liquido1.getVCL() + (ipentane_liquido2.getVCL() - ipentane_liquido1.getVCL()) * t1;
        Vcl2 = ipentane_liquido3.getVCL() + (ipentane_liquido4.getVCL() - ipentane_liquido3.getVCL()) * t2;
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
