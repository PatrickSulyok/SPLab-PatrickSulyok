
package ro.uvt.sp.lab1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import ro.uvt.sp.lab1.observer.AllBooksSubject;
import ro.uvt.sp.lab1.observer.SseObserver;

@RestController
public class BooksSseController {

    private final AllBooksSubject allBooksSubject;

    public BooksSseController(AllBooksSubject allBooksSubject) {
        this.allBooksSubject = allBooksSubject;
    }

    @GetMapping("/books-sse")
    public SseEmitter getBooksSse() {
        SseEmitter emitter = new SseEmitter(0L);
        SseObserver observer = new SseObserver(emitter);

        emitter.onCompletion(() -> allBooksSubject.detach(observer));
        emitter.onTimeout(() -> allBooksSubject.detach(observer));
        emitter.onError(error -> allBooksSubject.detach(observer));

        allBooksSubject.attach(observer);

        return emitter;
    }
}
