using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

public class ToDoDbContext : DbContext
{
    public ToDoDbContext(DbContextOptions<ToDoDbContext> options) : base(options) { }
    public DbSet<ToDo> ToDos { get; set; }
    public DbSet<ToDoDetail> ToDoDetails { get; set; }
}
