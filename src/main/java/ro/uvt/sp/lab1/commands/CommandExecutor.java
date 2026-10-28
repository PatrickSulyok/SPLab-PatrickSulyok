
package ro.uvt.sp.lab1.commands;

import java.util.concurrent.CompletableFuture;

public interface CommandExecutor {
    <T> CompletableFuture<T> execute(Command<T> command);
}
