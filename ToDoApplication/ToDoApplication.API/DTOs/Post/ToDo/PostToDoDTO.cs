using System.ComponentModel.DataAnnotations;
using System.Diagnostics.CodeAnalysis;

namespace ToDoApplication.API.DTOs.Post.ToDo
{
    public class PostToDoDTO
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
