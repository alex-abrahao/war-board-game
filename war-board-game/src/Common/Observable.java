package Common;

public interface Observable<ObserverType> {
    void addObserver(ObserverType observer);
    void removeObserver(ObserverType observer);
}