using AutoMapper;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ToDoApplication.BLL.BLLs.Get.ToDoDetail;
using ToDoApplication.BLL.BLLs.Post.ToDoDetail;
using ToDoApplication.BLL.Contexts;
using ToDoApplication.BLL.Exceptions;
using ToDoApplication.BLL.Models;

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

        public async Task<GetToDoDetailBLL> CreateToDoDetailAsync(PostToDoDetailBLL toDoDetailBLL, int todoId)
        {
            var toDo = await DbContext.ToDos.FirstOrDefaultAsync(t => t.Id == todoId);
            if (toDo == null)
            {
                throw new NotFoundException("ToDo not found");
            }
            var newToDoDetail = Mapper.Map<ToDoDetail>(toDoDetailBLL);
            newToDoDetail.ToDoId = todoId;
            newToDoDetail.ToDo = toDo;
            DbContext.ToDoDetails.Add(newToDoDetail);
            await DbContext.SaveChangesAsync();
            var newToDoDetailBll = Mapper.Map<GetToDoDetailBLL>(newToDoDetail);
            return newToDoDetailBll;
        }

        public async Task<List<GetToDoDetailBLL>> GetToDoDetailsAsync(int todoId)
        {
            var toDo = await DbContext.ToDos.FirstOrDefaultAsync(t => t.Id == todoId);
            if (toDo == null)
            {
                throw new NotFoundException("ToDo not found");
            }
            var toDoDetails = await DbContext.ToDoDetails.Where(td => td.ToDoId == todoId).ToListAsync();
            var toDoDetailsBll = Mapper.Map<List<GetToDoDetailBLL>>(toDoDetails);
            return toDoDetailsBll;
        }

        public async Task<ActionResult> DeleteToDoDetailByIdAsync(int toDoId, int toDoDetailId)
        {
            ToDo toDo = await DbContext.ToDos.FirstOrDefaultAsync(t => t.Id == toDoId);
            if (toDo == null)
            {
                throw new NotFoundException("ToDo not found");
            }

            ToDoDetail toDoDetail = await DbContext.ToDoDetails.FirstOrDefaultAsync(td => td.Id == toDoDetailId && td.ToDoId == toDoId);
            if (toDoDetail == null)
            {
                throw new NotFoundException("ToDoDetail not found");
            }

            DbContext.ToDoDetails.Remove(toDoDetail);
            await DbContext.SaveChangesAsync();
            return new ObjectResult($"ToDoDetail Deleted Successfully")
            {
                StatusCode = StatusCodes.Status200OK
            };
        }
    }
}
