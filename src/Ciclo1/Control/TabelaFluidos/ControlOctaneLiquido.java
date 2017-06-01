/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelOctaneLiquido;
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
public class ControlOctaneLiquido {
    
    Session session;
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2, kl1, kl2, Mul1, Mul2, Vcl1, Vcl2;
    
    public ControlOctaneLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaOctaneLiquido(){
        String csvFile = "src/Csv/OCTANE_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelOctaneLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] Octane_l = line.split(cvsSplitBy);
                    
                    this.session.save(new ModelOctaneLiquido(Double.parseDouble(Octane_l[0]), Double.parseDouble(Octane_l[1]), Double.parseDouble(Octane_l[2]), Double.parseDouble(Octane_l[3]), Double.parseDouble(Octane_l[4]), Double.parseDouble(Octane_l[5]),Double.parseDouble(Octane_l[6])));
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
        Criteria cr = this.session.createCriteria(ModelOctaneLiquido.class);
        //cr = this.session.createCriteria(ModelOctaneLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from Octane where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelOctaneLiquido> Octanes = consulta.list(); 
        ModelOctaneLiquido Octane1 = Octanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Octane where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneLiquido.class));//Sem isso aqui impossível de retornar
	Octanes = consulta.list(); 
        ModelOctaneLiquido Octane2 = Octanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Octane where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneLiquido.class));//Sem isso aqui impossível de retornar
	Octanes = consulta.list(); 
        ModelOctaneLiquido Octane3 = Octanes.get(0);
        
        consulta = this.session.createSQLQuery("select * from Octane where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelOctaneLiquido.class));//Sem isso aqui impossível de retornar
	Octanes = consulta.list(); 
        ModelOctaneLiquido Octane4 = Octanes.get(0);
        
        double p  = ((pressao - Octane1.getPRESSAO())/(Octane3.getPRESSAO() - Octane1.getPRESSAO()));
        double t1 = ((temperatura - Octane1.getTEMPERATURA())/(Octane2.getTEMPERATURA() - Octane1.getTEMPERATURA()));
        double t2 = ((temperatura - Octane3.getTEMPERATURA())/(Octane4.getTEMPERATURA() - Octane3.getTEMPERATURA()));
        
        Cpl1 = Octane1.getCPL()+ (Octane2.getCPL()- Octane1.getCPL()) * t1;
        Cpl2 = Octane3.getCPL()+ (Octane4.getCPL()- Octane3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = Octane1.getPRL()+ (Octane2.getPRL()- Octane1.getPRL()) * t1;
        Prl2 = Octane3.getPRL()+ (Octane4.getPRL()- Octane3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = Octane1.getKL() + (Octane2.getKL() - Octane1.getKL()) * t1;
        kl2 = Octane3.getKL() + (Octane4.getKL() - Octane3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = Octane1.getMUL() + (Octane2.getMUL() - Octane1.getMUL()) * t1;
        Mul2 = Octane3.getMUL() + (Octane4.getMUL() - Octane3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = Octane1.getVCL() + (Octane2.getVCL() - Octane1.getVCL()) * t1;
        Vcl2 = Octane3.getVCL() + (Octane4.getVCL() - Octane3.getVCL()) * t2;
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
