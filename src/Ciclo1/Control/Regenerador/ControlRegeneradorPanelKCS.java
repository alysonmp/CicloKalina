/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ciclo1.Control.Regenerador;

import Ciclo1.Control.ControlPrincipal;
import Ciclo1.View.Regenerador.ViewRegeneradorPanelKCS;

/**
 *
 * @author alysonmp
 */
public class ControlRegeneradorPanelKCS {

    ControlPrincipal ctrlPrincipal;
    ViewRegeneradorPanelKCS viewRegenerador;
    
    public ControlRegeneradorPanelKCS(ControlPrincipal ctrlPrincipal) {
        this.ctrlPrincipal = ctrlPrincipal;
        viewRegenerador = new ViewRegeneradorPanelKCS(this);
    }

    public ViewRegeneradorPanelKCS getViewRegenerador() {
        return viewRegenerador;
    }

    public void setViewRegenerador(ViewRegeneradorPanelKCS viewRegenerador) {
        this.viewRegenerador = viewRegenerador;
    }
}
