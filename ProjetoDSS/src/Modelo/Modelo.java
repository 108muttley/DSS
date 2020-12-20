package Modelo;

public interface Modelo {

    Boolean comunicaCodigoQR(String produto);

    Boolean comunicaOrdemDeTransporte();

    Boolean notificaRecolhaDePaletes();

    Boolean notificaEntregaDePaletes();

    Boolean consultarListagem();
}
