/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.TabelaFluidos;

import Model.TabelasFluidos.ModelMD2MLiquido;
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
    
    private double Cpl, Prl;
    private double Cpl1, Cpl2, Prl1, Prl2;

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
                    
                    this.session.save(new ModelMD2MLiquido(Double.parseDouble(md2m_liquido[0]),Double.parseDouble(md2m_liquido[1]),Double.parseDouble(md2m_liquido[2]),Double.parseDouble(md2m_liquido[3])));   
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
    
    public void interpolacaoMD2MLiquido(double pressao, double temperatura){
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
     
        double p1 = ((pressao - md2m_liquido1.getPRESSAO())/(md2m_liquido2.getPRESSAO() - md2m_liquido1.getPRESSAO()));
        double p2 = ((pressao - md2m_liquido3.getPRESSAO())/(md2m_liquido4.getPRESSAO() - md2m_liquido3.getPRESSAO()));
        double t1 = ((temperatura - md2m_liquido1.getTEMPERATURA())/(md2m_liquido2.getTEMPERATURA() - md2m_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - md2m_liquido3.getTEMPERATURA())/(md2m_liquido4.getTEMPERATURA() - md2m_liquido3.getTEMPERATURA()));
        
        Cpl1 = md2m_liquido1.getCPL()+ (md2m_liquido2.getCPL()- md2m_liquido1.getCPL()) * t1;
        Cpl2 = md2m_liquido3.getCPL()+ (md2m_liquido4.getCPL()- md2m_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p1;
        
        Prl1 = md2m_liquido1.getPRL()+ (md2m_liquido2.getPRL()- md2m_liquido1.getPRL()) * t1;
        Prl2 = md2m_liquido3.getPRL()+ (md2m_liquido4.getPRL()- md2m_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p2;
    }
}
