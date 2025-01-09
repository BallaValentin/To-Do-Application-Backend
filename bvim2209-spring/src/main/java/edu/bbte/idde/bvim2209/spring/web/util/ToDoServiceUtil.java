package edu.bbte.idde.bvim2209.spring.web.util;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;

public class ToDoServiceUtil {
    public void validateToDo(ToDo toDo) {
        validateTitle(toDo);
        validateDescription(toDo);
        validateDueDate(toDo);
        validateImportanceLevel(toDo);
    }

    private void validateTitle(ToDo toDo) {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
    }

    private void validateDescription(ToDo toDo) {
        if (toDo.getDescription() == null || toDo.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
    }

    private void validateDueDate(ToDo toDo) {
        if (toDo.getDueDate() == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
    }

    private void validateImportanceLevel(ToDo toDo) {
        if (toDo.getLevelOfImportance() == null) {
            throw new IllegalArgumentException("Importance level cannot be null");
        }
    }
}
