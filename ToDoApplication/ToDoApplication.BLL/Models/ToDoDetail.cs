using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class ToDoDetail
{
    [Key]
    private long Id { get; set; }
    [Required]
    private long todoId { get; set; }
    [Required]
    private string Text { get; set; }
}
