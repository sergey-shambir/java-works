package name.sshambir.controllers;

import name.sshambir.controllers.CashDeskQueue;
import name.sshambir.shop.PassiveCustomer;

public class CustomerController implements Runnable {
    PassiveCustomer customer;
    CashDeskQueue queue;

    @Override
    public void run() {
        // TODO: run customer actions on thread
    }
}
