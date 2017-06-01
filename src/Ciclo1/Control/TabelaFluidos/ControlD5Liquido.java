package Ciclo1.Control.TabelaFluidos;

import Ciclo1.Model.TabelasFluidos.ModelD5Liquido;
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
public class ControlD5Liquido {
    private Session session;
    
    private double Cpl, Prl, kl, Mul, Vcl;
    private double Cpl1, Cpl2, Prl1, Prl2,kl1 , kl2, Mul1, Mul2, Vcl1, Vcl2;

    public ControlD5Liquido(Session session) {
        this.session = session;
    }
    
    public void criaTabelaD5Liquido(){
        String csvFile = "src/Csv/D5_liquido.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ";";
      
        try{
            Criteria cr = this.session.createCriteria(ModelD5Liquido.class);
            List results = cr.list();
            
            if(results.isEmpty()){
                br = new BufferedReader(new FileReader(csvFile));
                line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] d5_liquido = line.split(csvSplitBy);
                    
                    session.save(new ModelD5Liquido(Double.parseDouble(d5_liquido[0]), Double.parseDouble(d5_liquido[1]), Double.parseDouble(d5_liquido[2]), Double.parseDouble(d5_liquido[3]), Double.parseDouble(d5_liquido[4]), Double.parseDouble(d5_liquido[5]),Double.parseDouble(d5_liquido[6])));
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
        Criteria cr = this.session.createCriteria(ModelD5Liquido.class);
        
        SQLQuery consulta = this.session.createSQLQuery("select * from d5_liquido where pressao <= " +pressao+ " and temperatura <= " +temperatura+ " ORDER BY ID DESC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD5Liquido.class));//Sem isso aqui impossível de retornar
	List<ModelD5Liquido> d5_liquido = consulta.list(); 
        ModelD5Liquido d5_liquido1 = d5_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d5_liquido where pressao <= "+pressao+" and temperatura >= "+temperatura+" ORDER BY PRESSAO DESC, TEMPERATURA ASC FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD5Liquido.class));//Sem isso aqui impossível de retornar
	d5_liquido = consulta.list(); 
        ModelD5Liquido d5_liquido2 = d5_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d5_liquido where pressao >= "+pressao+" and temperatura <= "+temperatura+" ORDER BY PRESSAO ASC, TEMPERATURA DESC");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD5Liquido.class));//Sem isso aqui impossível de retornar
	d5_liquido = consulta.list(); 
        ModelD5Liquido d5_liquido3 = d5_liquido.get(0);
        
        consulta = this.session.createSQLQuery("select * from d5_liquido where pressao >= " +pressao+ " and temperatura >= " +temperatura+ " FETCH FIRST 1 ROWS ONLY");
        
        consulta.setResultTransformer(Transformers.aliasToBean(ModelD5Liquido.class));//Sem isso aqui impossível de retornar
	d5_liquido = consulta.list(); 
        ModelD5Liquido d5_liquido4 = d5_liquido.get(0);
     
        double p = ((pressao - d5_liquido1.getPRESSAO())/(d5_liquido3.getPRESSAO() - d5_liquido1.getPRESSAO()));
        double t1 = ((temperatura - d5_liquido1.getTEMPERATURA())/(d5_liquido2.getTEMPERATURA() - d5_liquido1.getTEMPERATURA()));
        double t2 = ((temperatura - d5_liquido3.getTEMPERATURA())/(d5_liquido4.getTEMPERATURA() - d5_liquido3.getTEMPERATURA()));
        
        Cpl1 = d5_liquido1.getCPL()+ (d5_liquido2.getCPL()- d5_liquido1.getCPL()) * t1;
        Cpl2 = d5_liquido3.getCPL()+ (d5_liquido4.getCPL()- d5_liquido3.getCPL()) * t2;
        Cpl = Cpl1 + (Cpl2 - Cpl1) * p;
        
        Prl1 = d5_liquido1.getPRL()+ (d5_liquido2.getPRL()- d5_liquido1.getPRL()) * t1;
        Prl2 = d5_liquido3.getPRL()+ (d5_liquido4.getPRL()- d5_liquido3.getPRL()) * t2;
        Prl = Prl1 + (Prl2 - Prl1) * p;
        
        kl1 = d5_liquido1.getKL() + (d5_liquido2.getKL() - d5_liquido1.getKL()) * t1;
        kl2 = d5_liquido3.getKL() + (d5_liquido4.getKL() - d5_liquido3.getKL()) * t2;
        kl = kl1 + (kl2 - kl1) * p;
        
        Mul1 = d5_liquido1.getMUL() + (d5_liquido2.getMUL() - d5_liquido1.getMUL()) * t1;
        Mul2 = d5_liquido3.getMUL() + (d5_liquido4.getMUL() - d5_liquido3.getMUL()) * t2;
        Mul = Mul1 + (Mul2 - Mul1) * p;
        
        Vcl1 = d5_liquido1.getVCL() + (d5_liquido2.getVCL() - d5_liquido1.getVCL()) * t1;
        Vcl2 = d5_liquido3.getVCL() + (d5_liquido4.getVCL() - d5_liquido3.getVCL()) * t2;
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
