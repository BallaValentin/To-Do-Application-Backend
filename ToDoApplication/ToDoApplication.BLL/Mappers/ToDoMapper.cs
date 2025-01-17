using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ToDoApplication.BLL.BLLs.Get.ToDo;
using ToDoApplication.BLL.BLLs.Post.ToDo;
using ToDoApplication.BLL.BLLs.Put.ToDo;
using ToDoApplication.BLL.Models;

namespace ToDoApplication.BLL.Mappers
{
    public class ToDoMapper : Profile
    {
        public ToDoMapper() 
        {
            CreateMap<GetToDoBLL, ToDo>();
            CreateMap<PostToDoBLL, ToDo>();
            CreateMap<PutToDoBLL, ToDo>();

            CreateMap<ToDo, GetToDoBLL>();
            CreateMap<ToDo, PostToDoBLL>();
            CreateMap<ToDo, PutToDoBLL>();
        }
    }
}
