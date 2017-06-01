/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelHexaneLiquido;
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
public class ControlHexaneLiquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;

    public ControlHexaneLiquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaHexaneLiquido(){
        String csvFile = "src/Csv/HEXANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelHexaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] hexane_liquido = line.split(csvSplitBy);
                    
                    session.save(new ModelHexaneLiquido(Double.parseDouble(hexane_liquido[0]), Double.parseDouble(hexane_liquido[1]), Double.parseDouble(hexane_liquido[2]), Double.parseDouble(hexane_liquido[3]), Double.parseDouble(hexane_liquido[4]), Double.parseDouble(hexane_liquido[5]),Double.parseDouble(hexane_liquido[6])));
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
        Criteria cr = this.session.createCriteria(ModelHexaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from hexane_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelHexaneLiquido> hexane_liquido = consulta.list(); 
        ModelHexaneLiquido hexane_liquido1 = hexane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from hexane_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexaneLiquido.class));//Sem isso aqui impossível de retornar
	hexane_liquido = consulta.list(); 
        ModelHexaneLiquido hexane_liquido2 = hexane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from hexane_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexaneLiquido.class));//Sem isso aqui impossível de retornar
	hexane_liquido = consulta.list(); 
        ModelHexaneLiquido hexane_liquido3 = hexane_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from hexane_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelHexaneLiquido.class));//Sem isso aqui impossível de retornar
	hexane_liquido = consulta.list(); 
        ModelHexaneLiquido hexane_liquido4 = hexane_liquido.get(0);
     
        double p  = ((pressao - hexane_liquido1.getPRESSAO())/(hexane_liquido3.getPRESSAO() - hexane_liquido1.getPRESSAO()));
        double t1 = ((temperatura - hexane_liquido1.getTEMPERATURA())/(hexane_liquido2.getTEMPERATURA() - hexane_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - hexane_liquido3.getTEMPERATURA())/(hexane_liquido4.getTEMPERATURA() - hexane_liquido3.getTEMPERATURA()));
        
        Cpl1 = hexane_liquido1.getCPL()+ (hexane_liquido2.getCPL()- hexane_liquido1.getCPL()) * t1;
        Cpl2 = hexane_liquido3.getCPL()+ (hexane_liquido4.getCPL()- hexane_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = hexane_liquido1.getPRL()+ (hexane_liquido2.getPRL()- hexane_liquido1.getPRL()) * t1;
        Prl2 = hexane_liquido3.getPRL()+ (hexane_liquido4.getPRL()- hexane_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = hexane_liquido1.getKL() + (hexane_liquido2.getKL() - hexane_liquido1.getKL()) * t1;
        kl2 = hexane_liquido3.getKL() + (hexane_liquido4.getKL() - hexane_liquido3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = hexane_liquido1.getMUL() + (hexane_liquido2.getMUL() - hexane_liquido1.getMUL()) * t1;
        Mul2 = hexane_liquido3.getMUL() + (hexane_liquido4.getMUL() - hexane_liquido3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = hexane_liquido1.getVCL() + (hexane_liquido2.getVCL() - hexane_liquido1.getVCL()) * t1;
        Vcl2 = hexane_liquido3.getVCL() + (hexane_liquido4.getVCL() - hexane_liquido3.getVCL()) * t2;
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
