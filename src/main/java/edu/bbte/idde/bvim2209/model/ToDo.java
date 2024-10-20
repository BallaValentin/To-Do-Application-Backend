package edu.bbte.idde.bvim2209.model;

import java.util.Date;

public class ToDo extends BaseEntity {

    private String title;
    private String description;
    private Date dueDate;
    private Integer levelOfImportance;

    public ToDo() {
        super();
    }

    public ToDo(Long id, String title, String description, Date dueDate, Integer levelOfImportance) {
        super(id);
        this.title = title;
        this.description = description;
        this.dueDate = new Date(dueDate.getTime());
        this.levelOfImportance = levelOfImportance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = new Date(dueDate.getTime());
    }

    public Integer getLevelOfImportance() {
        return levelOfImportance;
    }

    public void setLevelOfImportance(Integer levelOfImportance) {
        this.levelOfImportance = levelOfImportance;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ToDoList{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dueDate='").append(dueDate).append('\'');
        sb.append(", levelOfImportance=").append(levelOfImportance);
        sb.append('}');
        return sb.toString();
    }

}


