/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelR11Liquido;
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
public class ControlR11Liquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlR11Liquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaR11Liquido(){
        String csvFile = "src/Csv/R11_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelR11Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use comma as separator
                    String[] R11_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelR11Liquido(Double.parseDouble(R11_l[0]), Double.parseDouble(R11_l[1]), Double.parseDouble(R11_l[2]), Double.parseDouble(R11_l[3])));
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
    
    public void interpolacaoButano(double pressao, double temperatura){
        Criteria cr = this.session.createCriteria(ModelR11Liquido.class);
        //cr = this.session.createCriteria(ModelR11Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from R11 where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelR11Liquido> R11s = consulta.list(); 
        ModelR11Liquido R111 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11 where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Liquido R112 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11 where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Liquido R113 = R11s.get(0);
        
        consulta = this.session.createSQLQuery("select * from R11 where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelR11Liquido.class));//Sem isso aqui impossível de retornar
	R11s = consulta.list(); 
        ModelR11Liquido R114 = R11s.get(0);
        
        Cpl1 = R111.getCPL()+ (R112.getCPL()- R111.getCPL()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        Cpl2 = R113.getCPL()+ (R114.getCPL()- R113.getCPL()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-R111.getPRESSAO())/(R112.getPRESSAO()-R111.getPRESSAO()));
        
        Prl1 = R111.getPRL()+ (R112.getPRL()- R111.getPRL()) * ((temperatura-R111.getTEMPERATURA())/(R112.getTEMPERATURA()-R111.getTEMPERATURA()));
        Prl2 = R113.getPRL()+ (R114.getPRL()- R113.getPRL()) * ((temperatura-R113.getTEMPERATURA())/(R114.getTEMPERATURA()-R113.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-R113.getPRESSAO())/(R114.getPRESSAO()-R113.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
