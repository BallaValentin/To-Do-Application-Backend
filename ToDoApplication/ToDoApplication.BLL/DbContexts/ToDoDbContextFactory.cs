using Microsoft.EntityFrameworkCore.Design;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ToDoApplication.BLL.Contexts
{
    public class ToDoDbContextFactory : IDesignTimeDbContextFactory<ToDoDbContext>
    {
        public ToDoDbContext CreateDbContext(string[] args)
        {
            var optionsBuilder = new DbContextOptionsBuilder<ToDoDbContext>();

            optionsBuilder.UseSqlServer("Server=DESKTOP-7289R3V;Database=ToDoDatabase;Trusted_Connection=True;TrustServerCertificate=True;");

            return new ToDoDbContext(optionsBuilder.Options);
        }
    }
}
