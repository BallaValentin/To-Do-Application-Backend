package edu.bbte.idde.bvim2209.backend.services;

public class ToDoServiceImplFactory {
    public static ToDoServiceImpl instance;

    public synchronized static ToDoServiceImpl getInstance() {
        if (instance == null) {
            instance = new ToDoServiceImpl();
        }
        return instance;
    }
}
