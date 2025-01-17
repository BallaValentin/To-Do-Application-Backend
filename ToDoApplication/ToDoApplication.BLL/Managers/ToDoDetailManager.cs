using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ToDoApplication.BLL.Contexts;

namespace ToDoApplication.BLL.Managers
{
    public class ToDoDetailManager
    {
        public ToDoDbContext DbContext { get; set; }
        public IMapper Mapper { get; set; }

        public ToDoDetailManager(ToDoDbContext dbContext, IMapper mapper)
        {
            DbContext = dbContext;
            Mapper = mapper;
        }
    }
}
