package Modelo;

import java.util.ArrayList;
import java.util.List;

public class LeitorQrCode {
    Sistema sistema;


    public LeitorQrCode(Sistema sistema){
        this.sistema = sistema;
    }

    public void comunicaNovaPalete(Palete p){
        List<Object> arg = new ArrayList<>();
        arg.add(p);
       // this.sistema.comunicaSistema("novaPalete",arg);
    }


    public void run(){
        Object o;
        while(true) {
            //while ((o = sistema.getPaleteWaitingForReading()) != null) {
            //    RawPalete rp = (RawPalete) o;
            //    Palete p = new Palete(rp.getCodigo(), "0-0", rp.getMaterial(), rp.getPreco(), rp.getListaDeMateriais());
            //    this.comunicaNovaPalete(p);
            //    System.out.println("E vai uma");
            //}
            //System.out.println("acabou temporariamente");
            //try {
            //    Thread.sleep(3000);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }
    }

}
