using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ToDoApplication.API.DTOs.Get.ToDo;
using ToDoApplication.API.DTOs.Post.ToDo;
using ToDoApplication.BLL.BLLs.Get.ToDo;
using ToDoApplication.BLL.BLLs.Post.ToDo;
using ToDoApplication.BLL.Models;

namespace ToDoApplication.BLL.Mappers
{
    public class ToDoMapper : Profile
    {
        public ToDoMapper() 
        {
            CreateMap<GetToDoBLL, GetToDoDTO>();
            CreateMap<PostToDoBLL, PostToDoBLL>();

            CreateMap<GetToDoDTO, GetToDoBLL>();
            CreateMap<PostToDoDTO, PostToDoBLL>();
        }
    }
}
