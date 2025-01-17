using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ToDoApplication.BLL.BLLs.Post.ToDoDetail
{
    public class PostToDoDetailBLL
    {
        [Required]
        public string Text { get; set; }
    }
}
