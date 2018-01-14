package name.sshambir.controllers;

import name.sshambir.controllers.CashDeskQueue;
import name.sshambir.shop.SolventClient;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class CashDeskDispatcher implements CashDeskQueue {
    BlockingDeque<SolventClient> queue;

    public CashDeskDispatcher() {
        this.queue = new LinkedBlockingDeque<>();
    }

    @Override
    public void addClient(SolventClient client) {
        this.queue.add(client);
    }

    @Override
    public SolventClient removeClient() throws InterruptedException {
        return this.queue.take();
    }
}
