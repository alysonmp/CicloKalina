/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control.Ciclo2;

/**
 *
 * @author alysonmp
 */
public class ControlCompequi {
    
    private double zi, Dzi, zj;
    private int erro;
    
    public ControlCompequi(double x, double yi){
       
        zi = 0.5;
        erro = 1;
        Dzi = 0.25;
        
        while(erro > 0.0001){
            zj = 1-zi;
            
        }
    }
}

function [x, yi]=compequi(P, T);


zi=0.5;

erro=1;
Dzi=0.25;

while erro>0.0001;
    zj=1-zi;
    [Ki, Kj]=conequi(T, P, zi);
    teste=(Ki*zi)+(Kj*zj);
    erro= abs((1-teste)/1);
    if erro>0.0001 & teste<1;
        zi=zi+Dzi;
        Dzi=Dzi/2;
        %if Dzi<0.005;
%                 Dzi=0.0047953543;
%             end
        else if erro>0.0001 & teste>1;
                zi=zi-Dzi;
                Dzi=Dzi/2;
%                 if Dzi<0.005;
%                     Dzi=0.00393845211111;
%                 end
            end
    end
    if zj<0.000001
        disp('Liquido subenfriado')
        kljlskfdjjklsf*lksfadfsdajk
    end
    if zi<0.000001
        disp('Vapor supÈraquecido')
        kljlskfdjjklsf*lksfadfsdajk
    end
    
end
x=zi;
yi=Ki*zi;
end