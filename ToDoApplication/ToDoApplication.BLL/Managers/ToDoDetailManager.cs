using AutoMapper;
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
    }
}
