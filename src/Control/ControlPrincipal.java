/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.Ciclo2.ModelBomba;
import Model.Ciclo2.ModelFluidos;
import Model.ModelConstantesMat;
import Util.HibernateUtil;
import View.Condensador.ViewCondensadorImage;
import View.Evaporador.ViewEvaporadorImage;
import View.Recuperador.ViewRecuperadorImage;
import View.Separador.ViewSeparadorImage;
import View.Bomba.ViewBombaImage;
import View.Evaporador.ViewEvaporadorPanelRankine;
import View.Regenerador.ViewRegeneradorImage;
import View.ViewPrincipal;
import View.Turbina.ViewTurbinaImage;
import View.ViewCiclos;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author alysonmp
 */
public class ControlPrincipal {
    private ViewPrincipal viewPrincipal;
    private ArrayList<JPanel> panel_usado = new ArrayList();
    Session session;
    
    public ControlPrincipal(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        List results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();

            session.save(new ModelFluidos(1, "Agua"));
            session.save(new ModelFluidos(2, "Toluene (C7H8)"));
            session.save(new ModelFluidos(3, "Ethylbenzene (C8H10)"));
            session.save(new ModelFluidos(4, "Propylbenzene (C9H12)"));
            session.save(new ModelFluidos(5, "Dimetylbenzene(C8H10)"));
            session.save(new ModelFluidos(6, "Octamethyltrisiloxane (MDM)"));
            session.save(new ModelFluidos(7, "Decamethyltetrasiloxane (MD2M)"));
            session.save(new ModelFluidos(8, "Dodecamethylpentasiloxane (MD3M)"));
            session.save(new ModelFluidos(9, "Dodecamethylcyclohexasiloxane (D6)"));
            session.save(new ModelFluidos(10, "Isobutano"));
            session.save(new ModelFluidos(11, "Isopentano"));
            session.save(new ModelFluidos(12, "n-Butano"));
            session.save(new ModelFluidos(13, "n-Pentano"));
            session.save(new ModelFluidos(14, "R-134a (1,1,1,2-Tetrafluoroethane)"));
            session.save(new ModelFluidos(15, "R-290 (PROPANE)"));
            session.save(new ModelFluidos(16, "R-142b (1,1-Difluoro-1-chloroethane)"));
            session.save(new ModelFluidos(17, "R-1270 (1-Propene)"));
            session.save(new ModelFluidos(18, "R-40  (Clorometano)"));
            session.save(new ModelFluidos(19, "Hexamethyldisiloxane, (MM)"));
            session.save(new ModelFluidos(20, "Tetradecamethylhexasiloxane (MD4M)"));
            session.save(new ModelFluidos(21, "Octamethylcyclotetrasiloxane (D4)"));
            session.save(new ModelFluidos(22, "Decamethylcyclopentasiloxane (D5)"));
            session.save(new ModelFluidos(23, "Trichlorofluoromethane (R11)"));
            session.save(new ModelFluidos(24, "Dichlorodifluoromethane (R12)"));
            session.save(new ModelFluidos(25, "1,3-Dichloro-1,1,2,2,3,3-hexafluoropropane (R216ca)"));
            session.save(new ModelFluidos(26, "1,2-Dichlorotetrafluoroethane (R114)"));
            session.save(new ModelFluidos(27, "1,1-Dichloro-1-fluoroethane (R141b)"));
            session.save(new ModelFluidos(28, "1,1-Difluoroethane (R152a)"));
            session.save(new ModelFluidos(29, "1-Chloro-1,1-difluoroethane (R142b)"));
            session.save(new ModelFluidos(30, "n-Hexane"));
            session.save(new ModelFluidos(31, "n-Heptane"));
            session.save(new ModelFluidos(32, "n-octane"));
            session.save(new ModelFluidos(33, "Chlorodifluoromethane (R22)"));

            tx.commit();
        }
        
        cr = this.session.createCriteria(ModelFluidos.class);
        results = cr.list();
        if(results.isEmpty()){
            Transaction tx = session.beginTransaction();
            
            double[][] valores = null;
            
            valores[0][0] = 90.483;
            valores[0][1] = -4669.7;
            valores[0][2] = -11.607;
            valores[0][3] = 0.017194;
            valores[0][4] = 1.0;
            session.save(new ModelConstantesMat("C1", valores));
            
            valores = null;
            valores[0][0] = 73.649;
            valores[0][1] = -7258.2;
            valores[0][2] = -7.3037;
            valores[0][3] = 4.1653E-6;
            valores[0][4] = 2.0;
            session.save(new ModelConstantesMat("C2", valores));

            valores = null;
            valores[0][0] = 33.60134;
            valores[0][1] = -0.002145;
            valores[0][2] = -3.999E-6;
            valores[0][3] = 4.752E-8;
            valores[0][4] = -3.533E-11;
            valores[0][5] = 0.0;
            valores[0][6] = 0.0;
            
            valores[1][0] = 33.573;
            valores[1][1] = -0.012581;
            valores[1][2] = 8.8906E-5;
            valores[1][3] = -7.1783E-8;
            valores[1][4] = 1.8569E-11;
            valores[1][5] = 0.0;
            valores[1][6] = 0.0;
            session.save(new ModelConstantesMat("cc", valores));
            
            tx.commit();
        }
        
        viewPrincipal = new ViewPrincipal(this);
    }
    
    //FUNÇÃO QUE CRIA O DESENHO DO PRIMEIRO CICLO E INDICA OS LOCAIS DOS JPANELS INSERIDOS
    public void criaCiclo1() {
        viewPrincipal.getPainelCiclos().removeAll();
        viewPrincipal.getTabbedPanel().removeAll();
        panel_usado.clear();
        
        ViewCiclos ciclo = new ViewCiclos(this, "src/Images/Ciclo1/ciclo1.png", (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.9));
        ciclo.setLayout(null);
        ciclo.setBounds((int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.1), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.05), 
                          (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.9));
        viewPrincipal.getPainelCiclos().add(ciclo);
        
        criaPanel(new ViewTurbinaImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.28), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.065), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.04), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewSeparadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.21), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.04), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1));
        
        criaPanel(new ViewEvaporadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.13), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.166), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.075), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewCondensadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.285), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.725), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.07), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewRecuperadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.184), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.35), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.069), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewRecuperadorImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.268), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.558), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.069), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.15));
        
        criaPanel(new ViewBombaImage(this, 1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.205), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.875), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.035), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.04));
        
        viewPrincipal.getFramePrincipal().repaint();
    }
    
    //FUNÇÃO QUE CRIA O DESENHO DO SEGUNDO CICLO E INDICA OS LOCAIS DOS JPANELS INSERIDOS
    public void criaCiclo2(){
        viewPrincipal.getPainelCiclos().removeAll();
        viewPrincipal.getTabbedPanel().removeAll();
        panel_usado.clear();
        
        ViewCiclos ciclo = new ViewCiclos(this, "src/Images/Ciclo2/ciclo2.png", (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.7));
        ciclo.setLayout(null);
        ciclo.setBounds((int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.1), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.05), 
                          (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.3), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.7));
        viewPrincipal.getPainelCiclos().add(ciclo);
        
        criaPanel(new ViewTurbinaImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.224), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.1), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.05), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewRegeneradorImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.236), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.288), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.06), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.13));
        
        criaPanel(new ViewEvaporadorImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.15), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.148), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.055), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.16));
        
        criaPanel(new ViewCondensadorImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.275), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.61), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.075), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.11));
        
        criaPanel(new ViewBombaImage(this, 2), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.326), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.38), 
                 (int)(this.getViewPrincipal().getFramePrincipal().getWidth()*0.065), (int)(this.getViewPrincipal().getFramePrincipal().getHeight()*0.14));
        
        viewPrincipal.getFramePrincipal().repaint();
    }
    
    //FUNÇÃO QUE EDITA OS JPANELS ONDE SERÃO MOSTRADOS OS CICLOS E OS INSERE NO PAINEL
    public void criaPanel(JPanel obj, int x, int y, int width, int height){
        obj.setLayout(null);
        obj.setBackground(Color.black);
        obj.setOpaque(false);
        obj.setBounds(x, y, width, height);
        viewPrincipal.getPainelCiclos().add(obj);
    }  
    
    //FUNÇÃO QUE AJUSTA A MASSA DE TODOS OS COMPONENTES
    public void ajustaMassa() {
        Component[] components = viewPrincipal.getTabbedPanel().getComponents();
        for(Component c: components){
            switch(c.getName()){
                case "Evaporador":
                    ViewEvaporadorPanelRankine painel = (ViewEvaporadorPanelRankine)c;
                    //painel.
                    break;
            }
        }
    }
    
    public void removeTab(){
        this.getViewPrincipal().getTabbedPanel().remove(0);
        this.getPanel_usado().remove(0);
    }
    
    public List getFluidos(){
        SessionFactory sf = HibernateUtil.getSessionFactory();
        this.session = sf.openSession();
        
        Criteria cr = this.session.createCriteria(ModelFluidos.class);
        cr.setProjection(Projections.property("nome"));
        
        List results = cr.list();
        
        return results;
    }
    
    public ViewPrincipal getViewPrincipal() {
        return viewPrincipal;
    }

    public void setViewPrincipal(ViewPrincipal viewPrincipal) {
        this.viewPrincipal = viewPrincipal;
    }

    public ArrayList<JPanel> getPanel_usado() {
        return panel_usado;
    }

    public void setPanel_usado(ArrayList<JPanel> panel_usado) {
        this.panel_usado = panel_usado;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}


