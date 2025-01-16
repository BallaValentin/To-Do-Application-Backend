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

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        // Kapcsolat a ToDo és ToDoDetail között
        modelBuilder.Entity<ToDoDetail>()
            .HasOne(td => td.ToDo)
            .WithMany(t => t.Details)
            .HasForeignKey(td => td.ToDoId);
    }
}
