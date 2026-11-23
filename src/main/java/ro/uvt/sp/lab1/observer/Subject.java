
package ro.uvt.sp.lab1.observer;

import ro.uvt.sp.lab1.Book;

public interface Subject {

    void attach(Observer observer);

    void detach(Observer observer);

    void notifyObservers(Book book);
}
