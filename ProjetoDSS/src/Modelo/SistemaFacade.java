package Modelo;

public interface SistemaFacade {

    String comunicaCodigoQR(String produto);

    String comunicaOrdemDeTransporte();

    String notificaRecolhaDePaletes();

    String notificaEntregaDePaletes();

    String consultaListagem();
}
