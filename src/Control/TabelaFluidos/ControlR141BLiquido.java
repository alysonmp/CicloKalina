/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR141BLiquido;
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
public class ControlR141BLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR141BLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR141BLiquido(){
        String csvFile = "src/Csv/R141B_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR141BLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R141B_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR141BLiquido(Double.parseDouble(R141B_l[0]), Double.parseDouble(R141B_l[1]), Double.parseDouble(R141B_l[2]), Double.parseDouble(R141B_l[3])));
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
        Criteria cr = this.session.createCriteria(ModelR141BLiquido.class);
        //cr = this.session.createCriteria(ModelR141BLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R141B where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR141BLiquido> R141Bs = consulta.list(); 
        ModelR141BLiquido R141B1 = R141Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R141B where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BLiquido.class));//Sem isso aqui impossível de retornar
	R141Bs = consulta.list(); 
        ModelR141BLiquido R141B2 = R141Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R141B where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BLiquido.class));//Sem isso aqui impossível de retornar
	R141Bs = consulta.list(); 
        ModelR141BLiquido R141B3 = R141Bs.get(0);
        
        consulta = this.session.createSQLQuery("select * from R141B where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR141BLiquido.class));//Sem isso aqui impossível de retornar
	R141Bs = consulta.list(); 
        ModelR141BLiquido R141B4 = R141Bs.get(0);
        
        Cpl1 = R141B1.getCPL()+ (R141B2.getCPL()- R141B1.getCPL()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        Cpl2 = R141B3.getCPL()+ (R141B4.getCPL()- R141B3.getCPL()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R141B1.getPRESSAO())/(R141B2.getPRESSAO()-R141B1.getPRESSAO()));
        
        Prl1 = R141B1.getPRL()+ (R141B2.getPRL()- R141B1.getPRL()) * ((temperatura-R141B1.getTEMPERATURA())/(R141B2.getTEMPERATURA()-R141B1.getTEMPERATURA()));
        Prl2 = R141B3.getPRL()+ (R141B4.getPRL()- R141B3.getPRL()) * ((temperatura-R141B3.getTEMPERATURA())/(R141B4.getTEMPERATURA()-R141B3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R141B3.getPRESSAO())/(R141B4.getPRESSAO()-R141B3.getPRESSAO()));
        
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
