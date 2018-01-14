package name.sshambir.controllers;

import java.time.LocalTime;

public interface RunnableContext {
    LocalTime getCurrentTime();
    void sleepUntil(LocalTime localTime) throws InterruptedException;
}
