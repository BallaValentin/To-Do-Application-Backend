namespace ToDoApplication.API.DTOs.Put.ToDo
{
    public class PutToDoDTO
    {
        public string Title { get; set; }
        public string Description { get; set; }
        public DateOnly DueDate { get; set; }
        public int Priority { get; set; }
    }
}
