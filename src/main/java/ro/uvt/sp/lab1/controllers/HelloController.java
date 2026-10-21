
package ro.uvt.sp.lab1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.uvt.sp.lab1.diexamples.ClientComponent;

@RestController
public class HelloController {

    private final ClientComponent client1;
    private final ClientComponent client2;

    public HelloController(ClientComponent client1, ClientComponent client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    @GetMapping("/")
    public String hello() {
        return "Hello from ClientComponent = " + client1;
    }

    @GetMapping(value = "/clients", produces = "text/plain")
    public String compareClients() {
        return "Client 1: " + client1
                + "\nClient 2: " + client2
                + "\nSame instance: " + (client1 == client2);
    }
}
