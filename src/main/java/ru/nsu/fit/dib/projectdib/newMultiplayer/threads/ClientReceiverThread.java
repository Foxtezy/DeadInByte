package ru.nsu.fit.dib.projectdib.newMultiplayer.threads;

import ru.nsu.fit.dib.projectdib.newMultiplayer.context.client.MCClient;

public class ClientReceiverThread extends Thread {



  @Override
  public void run() {
    //тут короче свич
    //либо обновляем состояния
    //MCClient.getClientState().updateEntities(gameStatePacket.getEntitiesStates());
    //либо вот это вот
    //action.run()
  }

}
