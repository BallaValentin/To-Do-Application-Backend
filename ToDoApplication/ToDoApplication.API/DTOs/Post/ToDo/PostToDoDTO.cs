namespace ToDoApplication.API.DTOs.Post.ToDo
{
    public class PostToDoDTO
    {
        public String Title { get; set; }
        public String Description { get; set; }
        public DateOnly DueDate { get; set; }
        public int Priority { get; set; }
    }
}
