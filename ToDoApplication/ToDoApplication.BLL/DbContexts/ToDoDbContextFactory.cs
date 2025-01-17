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
        private readonly string ConnectionString;

        public ToDoDbContextFactory(string connectionString)
        {
            this.ConnectionString = connectionString;
        }
        public ToDoDbContext CreateDbContext(string[] args)
        {
            var optionsBuilder = new DbContextOptionsBuilder<ToDoDbContext>();

            optionsBuilder.UseSqlServer(ConnectionString);

            return new ToDoDbContext(optionsBuilder.Options);
        }
    }
}
