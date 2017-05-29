/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelMD4MLiquido;
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
    private double Cpl1, Cpl2, Prl1, Prl2;
    
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
                    
                    session.save(new ModelMD4MLiquido(Double.parseDouble(MD4M_l[0]), Double.parseDouble(MD4M_l[1]), Double.parseDouble(MD4M_l[2]), Double.parseDouble(MD4M_l[3])));
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
        
        Cpl1 = MD4M1.getCPL()+ (MD4M2.getCPL()- MD4M1.getCPL()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        Cpl2 = MD4M3.getCPL()+ (MD4M4.getCPL()- MD4M3.getCPL()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-MD4M1.getPRESSAO())/(MD4M2.getPRESSAO()-MD4M1.getPRESSAO()));
        
        Prl1 = MD4M1.getPRL()+ (MD4M2.getPRL()- MD4M1.getPRL()) * ((temperatura-MD4M1.getTEMPERATURA())/(MD4M2.getTEMPERATURA()-MD4M1.getTEMPERATURA()));
        Prl2 = MD4M3.getPRL()+ (MD4M4.getPRL()- MD4M3.getPRL()) * ((temperatura-MD4M3.getTEMPERATURA())/(MD4M4.getTEMPERATURA()-MD4M3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-MD4M3.getPRESSAO())/(MD4M4.getPRESSAO()-MD4M3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
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
