package Ciclo1.Control.Conversao;

/**
 *
 * @author leonardo
 */
public class ControlConverte {
    
    public ControlConverte(){
    }
    
    public double converte(String tipo,double valor){
        switch (tipo) {
            case "°C":
                return (valor + 273);
            case "°F":
                return ((5*valor + 2298.35)/9);
            case "atm":
                return (valor*101.325);
            case "bar":
                return (valor * 100);
            case "kg/m":
                return (valor*60);
            case "kg/h":
                return (valor * 3600);
            case "lb/s":
                return (valor * 2.204623);
            case "lb/m":
                return (valor * 132.277357);
            case "lb/h":
                return (valor * 7936.641438);
            default:
                return valor;
        }   
    }
}
