/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelMDMLiquido;
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
public class ControlMDMLiquido {
    
    Session session;
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;
    
    public ControlMDMLiquido(Session session){
        this.session = session;
    }
    
    public void criaTabelaMDMLiquido(){
        String csvFile = "src/Csv/MDM_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        this.session = session;
        
        try {

            Criteria cr = this.session.createCriteria(ModelMDMLiquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                Transaction tx = session.beginTransaction();
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while ((line = br.readLine()) != null) {

                    // use coMDMa as separator
                    String[] MDM_l = line.split(cvsSplitBy);
                    
                    session.save(new ModelMDMLiquido(Double.parseDouble(MDM_l[0]), Double.parseDouble(MDM_l[1]), Double.parseDouble(MDM_l[2]), Double.parseDouble(MDM_l[3])));
                }
                
                tx.commit();
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
        Criteria cr = this.session.createCriteria(ModelMDMLiquido.class);
        //cr = this.session.createCriteria(ModelMDMLiquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from MDM where pressao <= " +pressao+ "and temperatura <= " +temperatura+ "ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	List<ModelMDMLiquido> MDMs = consulta.list(); 
        ModelMDMLiquido MDM1 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMLiquido MDM2 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMLiquido MDM3 = MDMs.get(0);
        
        consulta = this.session.createSQLQuery("select * from MDM where pressao >= " +pressao+ "and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelMDMLiquido.class));//Sem isso aqui impossível de retornar
	MDMs = consulta.list(); 
        ModelMDMLiquido MDM4 = MDMs.get(0);
        
        Cpl1 = MDM1.getCPL()+ (MDM2.getCPL()- MDM1.getCPL()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        Cpl2 = MDM3.getCPL()+ (MDM4.getCPL()- MDM3.getCPL()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        Cpl = Cpl1 + (Cpl2 - Cpl1) * ((pressao-MDM1.getPRESSAO())/(MDM2.getPRESSAO()-MDM1.getPRESSAO()));
        
        Prl1 = MDM1.getPRL()+ (MDM2.getPRL()- MDM1.getPRL()) * ((temperatura-MDM1.getTEMPERATURA())/(MDM2.getTEMPERATURA()-MDM1.getTEMPERATURA()));
        Prl2 = MDM3.getPRL()+ (MDM4.getPRL()- MDM3.getPRL()) * ((temperatura-MDM3.getTEMPERATURA())/(MDM4.getTEMPERATURA()-MDM3.getTEMPERATURA()));
        Prl = Prl1 + (Prl2 - Prl1) * ((pressao-MDM3.getPRESSAO())/(MDM4.getPRESSAO()-MDM3.getPRESSAO()));
        
        System.out.println(Cpl);
        System.out.println(Prl);
    }    
}
