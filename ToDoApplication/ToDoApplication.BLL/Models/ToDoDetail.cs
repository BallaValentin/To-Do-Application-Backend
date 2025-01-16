using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ToDoApplication.BLL.Models
{
    public class ToDoDetail
    {
        [Key]
        public int Id { get; set; }

        [ForeignKey("ToDo")]
        public int ToDoId { get; set; }

        public virtual ToDo ToDo { get; set; }

        public string Text { get; set; }
    }
}
