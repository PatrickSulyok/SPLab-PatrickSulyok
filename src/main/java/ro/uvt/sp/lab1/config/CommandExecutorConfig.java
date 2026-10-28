
package ro.uvt.sp.lab1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.uvt.sp.lab1.commands.AsynchronousCommandExecutor;
import ro.uvt.sp.lab1.commands.SynchronousCommandExecutor;

@Configuration
public class CommandExecutorConfig {

    @Bean
    public SynchronousCommandExecutor synchronousCommandExecutor() {
        return new SynchronousCommandExecutor();
    }

    @Bean(destroyMethod = "close")
    public AsynchronousCommandExecutor asynchronousCommandExecutor() {
        return new AsynchronousCommandExecutor();
    }
}

