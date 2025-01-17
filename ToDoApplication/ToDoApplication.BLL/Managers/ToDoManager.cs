using AutoMapper;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ToDoApplication.BLL.BLLs.Get.ToDo;
using ToDoApplication.BLL.BLLs.Post.ToDo;
using ToDoApplication.BLL.Contexts;
using ToDoApplication.BLL.Models;

namespace ToDoApplication.BLL.Managers
{
    public class ToDoManager
    {
        public ToDoDbContext DbContext { get; set; }
        public IMapper Mapper { get; set; }
        public ToDoManager(ToDoDbContext context, IMapper mapper) 
        {
            DbContext = context;
            Mapper = mapper;
        }

        public async Task<GetToDoBLL> CreateToDoAsync(PostToDoBLL postToDo)
        {
            var toDo = Mapper.Map<ToDo>(postToDo);
            DbContext.ToDos.Add(toDo);
            await DbContext.SaveChangesAsync();
            var toDoBll = Mapper.Map<GetToDoBLL>(toDo);
            return toDoBll;
        }

        public async Task<List<GetToDoBLL>> GetToDosAsync()
        {
            List<ToDo> todos = await DbContext.ToDos.ToListAsync();
            List<GetToDoBLL> todosBll = Mapper.Map<List<GetToDoBLL>>(todos);
            return todosBll;
        }
    }
}
