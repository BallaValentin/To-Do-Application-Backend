using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ToDoApplication.BLL.BLLs.Get.ToDoDetail;
using ToDoApplication.BLL.BLLs.Post.ToDoDetail;
using ToDoApplication.BLL.Models;

namespace ToDoApplication.BLL.Mappers
{
    public class ToDoDetailMapper : Profile
    {
        public ToDoDetailMapper() 
        {
            CreateMap<GetToDoDetailBLL, ToDoDetail>();
            CreateMap<PostToDoDetailBLL, ToDoDetail>();

            CreateMap<ToDoDetail, GetToDoDetailBLL>();
            CreateMap<ToDoDetail, PostToDoDetailBLL>();
        }
    }
}
