
package ro.uvt.sp.lab1.commands;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousCommandExecutor implements CommandExecutor, AutoCloseable {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    @Override
    public <T> CompletableFuture<T> execute(Command<T> command) {
        return CompletableFuture.supplyAsync(command::execute, threadPool);
    }

    @Override
    public void close() {
        threadPool.shutdown();
    }
}
