using System.ComponentModel.DataAnnotations;

namespace ToDoApplication.BLL.BLLs.Post.ToDo
{
    public class PostToDoBLL
    {
        [Required]
        public String Title { get; set; }
        [Required]
        public String Description { get; set; }
        [Required]
        public DateOnly DueDate { get; set; }
        [Required]
        public int Priority { get; set; }
    }
}
