using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class ToDo
{
    [Key]
    private long Id { get; set; }
    [Required]
    private string Title { get; set; }
    [Required]
    private string Description { get; set; }
    [Required]
    private DateOnly dueDate { get; set; }
    [Required]
    private int Priority { get; set; }

    private List<ToDoDetail> Details { get; set; }
}
