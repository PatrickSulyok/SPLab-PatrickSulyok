
package ro.uvt.sp.lab1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ro.uvt.sp.lab1.diexamples.ClientComponent;
import ro.uvt.sp.lab1.diexamples.SingletonComponent;
import ro.uvt.sp.lab1.diexamples.TransientComponent;

@SpringBootApplication
public class SpLabPatrickSulyokApplication {

    public static void main(String[] args) {
        ApplicationContext context =
                SpringApplication.run(SpLabPatrickSulyokApplication.class, args);

        TransientComponent transientBean =
                context.getBean(TransientComponent.class);
        transientBean.operation();

        transientBean = context.getBean(TransientComponent.class);
        transientBean.operation();

        SingletonComponent singletonBean =
                context.getBean(SingletonComponent.class);
        singletonBean.operation();

        singletonBean = context.getBean(SingletonComponent.class);
        singletonBean.operation();

        ClientComponent client = context.getBean(ClientComponent.class);
        client.operation();

        client = (ClientComponent) context.getBean("clientComponent");
        client.operation();
    }
}
