/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelR216_CALiquido;
import Ciclo1.Model.TabelasFluidos.ModelR216_CALiquido;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 *
 * @author leonardo
 */
public class ControlR216_CALiquido {
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlR216_CALiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR216_CALiquido(){
        String csvFile = "src/Csv/r216_ca_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        
        try {

            Criteria cr = this.session.createCriteria(ModelR216_CALiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] r216_ca_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelR216_CALiquido(Double.parseDouble(r216_ca_l[0]), Double.parseDouble(r216_ca_l[1]), Double.parseDouble(r216_ca_l[2]), Double.parseDouble(r216_ca_l[3]), Double.parseDouble(r216_ca_l[4]), Double.parseDouble(r216_ca_l[5]),Double.parseDouble(r216_ca_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelR216_CALiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from r216_ca_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR216_CALiquido.class));//Sem isso aqui impossível de retornar
	List<ModelR216_CALiquido> r216_ca_l = consulta.list(); 
        ModelR216_CALiquido r216_ca_l1 = r216_ca_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from r216_ca_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR216_CALiquido.class));//Sem isso aqui impossível de retornar
	r216_ca_l = consulta.list(); 
        ModelR216_CALiquido r216_ca_l2 = r216_ca_l.get(0);

        consulta = this.session.createSQLQuery("select * from r216_ca_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR216_CALiquido.class));//Sem isso aqui impossível de retornar
	r216_ca_l = consulta.list(); 
        ModelR216_CALiquido r216_ca_l3 = r216_ca_l.get(0);
        
        consulta = this.session.createSQLQuery("select * from r216_ca_liquido where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR216_CALiquido.class));//Sem isso aqui impossível de retornar
	r216_ca_l = consulta.list(); 
        ModelR216_CALiquido r216_ca_l4 = r216_ca_l.get(0);
        
        double p  = ((pressao - r216_ca_l1.getPRESSAO())/(r216_ca_l3.getPRESSAO() - r216_ca_l1.getPRESSAO()));
        double t1 = ((temperatura - r216_ca_l1.getTEMPERATURA())/(r216_ca_l2.getTEMPERATURA() - r216_ca_l1.getTEMPERATURA()));
        double t2 = ((temperatura - r216_ca_l3.getTEMPERATURA())/(r216_ca_l4.getTEMPERATURA() - r216_ca_l3.getTEMPERATURA()));
        
        Cpl1 = r216_ca_l1.getCPL()+ (r216_ca_l2.getCPL()- r216_ca_l1.getCPL()) * t1;
        Cpl2 = r216_ca_l3.getCPL()+ (r216_ca_l4.getCPL()- r216_ca_l3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = r216_ca_l1.getPRL()+ (r216_ca_l2.getPRL()- r216_ca_l1.getPRL()) * t1;
        Prl2 = r216_ca_l3.getPRL()+ (r216_ca_l4.getPRL()- r216_ca_l3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = r216_ca_l1.getKL() + (r216_ca_l2.getKL() - r216_ca_l1.getKL()) * t1;
        kl2 = r216_ca_l3.getKL() + (r216_ca_l4.getKL() - r216_ca_l3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = r216_ca_l1.getMUL() + (r216_ca_l2.getMUL() - r216_ca_l1.getMUL()) * t1;
        Mul2 = r216_ca_l3.getMUL() + (r216_ca_l4.getMUL() - r216_ca_l3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = r216_ca_l1.getVCL() + (r216_ca_l2.getVCL() - r216_ca_l1.getVCL()) * t1;
        Vcl2 = r216_ca_l3.getVCL() + (r216_ca_l4.getVCL() - r216_ca_l3.getVCL()) * t2;
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
