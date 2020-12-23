package Modelo;

import Exceptions.*;

public interface SistemaFacade {

    String comunicaCodigoQR(String produto);

    String comunicaOrdemDeTransporte() throws NoPaletesOnWaitingListException, NoPrateleirasAvailableException, NoRobotAvailableException;

    String notificaRecolhaDePaletes() throws NoPaletesToCollectException, NoRobotAvailableException;

    String notificaEntregaDePaletes() throws NoPaletesToDeliverException, NoPrateleirasAvailableException;

    String consultaListagem() throws NoExistingPaletesException;
}
