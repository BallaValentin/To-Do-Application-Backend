using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class ToDo
{
    private long id { get; set; }
    private string title { get; set; }
    private string description { get; set; }
    private DateOnly dueDate { get; set; }

    private int priority { get; set; }

    private List<ToDoDetail> details { get; set; }
}
