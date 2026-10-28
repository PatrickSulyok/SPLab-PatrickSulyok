
package ro.uvt.sp.lab1.commands;

import java.util.concurrent.CompletableFuture;

public class SynchronousCommandExecutor implements CommandExecutor {

    @Override
    public <T> CompletableFuture<T> execute(Command<T> command) {
        try {
            T result = command.execute();
            return CompletableFuture.completedFuture(result);
        } catch (Exception e) {
            return CompletableFuture.failedFuture(e);
        }
    }
}

