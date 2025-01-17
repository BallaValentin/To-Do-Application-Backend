using ToDoApplication.BLL.Models;

namespace ToDoApplication.BLL.BLLs.Get.ToDo
{
    public class GetToDoBLL
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public DateOnly DueDate { get; set; }
        public int Priority { get; set; }
    }
}
