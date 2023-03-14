package ru.nsu.fit.dib.projectdib.multiplayer;

public class ClientThread implements Runnable {

  private final Client client = new Client();

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {

    }
  }
}
