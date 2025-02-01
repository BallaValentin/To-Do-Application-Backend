package edu.bbte.idde.bvim2209.backend.services;

public class ToDoServiceImplFactory {
    private static ToDoServiceImpl instance;

    public static synchronized ToDoServiceImpl getInstance() {
        if (instance == null) {
            instance = new ToDoServiceImpl();
        }
        return instance;
    }
}
