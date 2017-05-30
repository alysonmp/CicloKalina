/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelD6Liquido;
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
public class ControlD6Liquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2;

    public ControlD6Liquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaD6Liquido(){
        String csvFile = "src/Csv/D6_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelD6Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] d6_liquido = line.split(csvSplitBy);
                    
                    this.session.save(new ModelD6Liquido(Double.parseDouble(d6_liquido[0]),Double.parseDouble(d6_liquido[1]),Double.parseDouble(d6_liquido[2]),Double.parseDouble(d6_liquido[3])));   
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
        Criteria cr = this.session.createCriteria(ModelD6Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from d6_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelD6Liquido> d6_liquido = consulta.list(); 
        ModelD6Liquido d6_liquido1 = d6_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d6_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Liquido.class));//Sem isso aqui impossível de retornar
	d6_liquido = consulta.list(); 
        ModelD6Liquido d6_liquido2 = d6_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d6_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Liquido.class));//Sem isso aqui impossível de retornar
	d6_liquido = consulta.list(); 
        ModelD6Liquido d6_liquido3 = d6_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d6_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD6Liquido.class));//Sem isso aqui impossível de retornar
	d6_liquido = consulta.list(); 
        ModelD6Liquido d6_liquido4 = d6_liquido.get(0);
     
        double p1 = ((pressao - d6_liquido1.getPRESSAO())/(d6_liquido2.getPRESSAO() - d6_liquido1.getPRESSAO()));
        double p2 = ((pressao - d6_liquido3.getPRESSAO())/(d6_liquido4.getPRESSAO() - d6_liquido3.getPRESSAO()));
        double t1 = ((temperatura - d6_liquido1.getTEMPERATURA())/(d6_liquido2.getTEMPERATURA() - d6_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - d6_liquido3.getTEMPERATURA())/(d6_liquido4.getTEMPERATURA() - d6_liquido3.getTEMPERATURA()));
        
        Cpl1 = d6_liquido1.getCPL()+ (d6_liquido2.getCPL()- d6_liquido1.getCPL()) * t1;
        Cpl2 = d6_liquido3.getCPL()+ (d6_liquido4.getCPL()- d6_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p1;
        
        Prl1 = d6_liquido1.getPRL()+ (d6_liquido2.getPRL()- d6_liquido1.getPRL()) * t1;
        Prl2 = d6_liquido3.getPRL()+ (d6_liquido4.getPRL()- d6_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p2;
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
