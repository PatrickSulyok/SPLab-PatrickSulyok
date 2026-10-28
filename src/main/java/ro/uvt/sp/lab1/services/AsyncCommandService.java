
package ro.uvt.sp.lab1.services;

import org.springframework.stereotype.Service;
import ro.uvt.sp.lab1.commands.AsynchronousCommandExecutor;
import ro.uvt.sp.lab1.commands.Command;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AsyncCommandService {

    private final AsynchronousCommandExecutor executor;
    private final Map<String, CompletableFuture<?>> tasks = new ConcurrentHashMap<>();

    public AsyncCommandService(AsynchronousCommandExecutor executor) {
        this.executor = executor;
    }

    public <T> String submit(Command<T> command) {
        String taskId = UUID.randomUUID().toString();
        CompletableFuture<T> future = executor.execute(command);
        tasks.put(taskId, future);
        return taskId;
    }

    public Map<String, Object> getStatus(String taskId) {
        CompletableFuture<?> future = tasks.get(taskId);

        if (future == null) {
            return Map.of("status", "NOT_FOUND");
        }

        if (!future.isDone()) {
            return Map.of("status", "PENDING");
        }

        if (future.isCompletedExceptionally() || future.isCancelled()) {
            return Map.of("status", "FAILED");
        }

        Object result = future.join();

        return Map.of(
                "status", "COMPLETED",
                "result", result
        );
    }
}
