using System.ComponentModel.DataAnnotations;

namespace ToDoApplication.API.DTOs.Post.ToDoDetail
{
    public class PostToDoDetailDTO
    {
        [Required]
        public string Text { get; set; }
    }
}
