package name.sshambir.controllers;

import name.sshambir.shop.SolventClient;

public interface CashDeskQueue {
    void addClient(SolventClient client);
    SolventClient removeClient() throws InterruptedException;
}
