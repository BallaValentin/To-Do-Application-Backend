using ToDoApplication.BLL.Models;

namespace ToDoApplication.API.DTOs.Get.ToDo
{
    public class GetToDoDTO
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateOnly DueDate { get; set; }
        public int Prority { get; set; }
        public List<ToDoDetail> Details { get; set; } = new List<ToDoDetail>();
    }
}
