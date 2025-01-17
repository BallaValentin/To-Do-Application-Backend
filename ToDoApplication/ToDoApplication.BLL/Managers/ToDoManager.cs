using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Web.Mvc;
using ToDoApplication.BLL.BLLs.Get.ToDo;
using ToDoApplication.BLL.BLLs.Post.ToDo;
using ToDoApplication.BLL.BLLs.Put.ToDo;
using ToDoApplication.BLL.Contexts;
using ToDoApplication.BLL.Exceptions;
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

        public async Task<GetToDoBLL> GetToDoByIdAsync(int id)
        {
            ToDo toDo = await DbContext.ToDos.FirstOrDefaultAsync(t => t.Id == id);
            if (toDo == null)
            {
                throw new NotFoundException("ToDo not found");
            }
            GetToDoBLL toDoBll = Mapper.Map<GetToDoBLL>(toDo);
            return toDoBll;
        }

        public async Task<GetToDoBLL> UpdateToDoByIdAsync(PutToDoBLL toDoBll, int id)
        {
            ToDo toDo = await DbContext.ToDos.FirstOrDefaultAsync(t =>t.Id == id);
            if (toDo == null)
            {
                throw new NotFoundException("ToDo not found");
            }
            Mapper.Map(toDoBll, toDo);
            await DbContext.SaveChangesAsync();
            GetToDoBLL updatedToDo = Mapper.Map<GetToDoBLL>(toDo);
            return updatedToDo;
        }

        public async Task<Microsoft.AspNetCore.Mvc.ActionResult> DeleteToDoByIdAsync(int id)
        {
            ToDo toDo = await DbContext.ToDos.FirstOrDefaultAsync(t => t.Id == id);
            if (toDo == null)
            {
                throw new NotFoundException("ToDo not found");
            }
            DbContext.ToDos.Remove(toDo);
            await DbContext.SaveChangesAsync();
            return new ObjectResult($"ToDo Deleted Successfully")
            {
                StatusCode = StatusCodes.Status200OK
            };
        }
    }
}
