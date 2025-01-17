using AutoMapper;
using ToDoApplication.API.DTOs.Get.ToDoDetail;
using ToDoApplication.API.DTOs.Post.ToDoDetail;
using ToDoApplication.BLL.BLLs.Get.ToDoDetail;
using ToDoApplication.BLL.BLLs.Post.ToDoDetail;

namespace ToDoApplication.API.Mappers
{
    public class ToDoDetailMapper : Profile
    {
        public ToDoDetailMapper() 
        {
            CreateMap<GetToDoDetailBLL, GetToDoDetailDTO>();
            CreateMap<PostToDoDetailBLL, PostToDoDetailDTO>();

            CreateMap<GetToDoDetailDTO, GetToDoDetailBLL>();
            CreateMap<PostToDoDetailDTO, PostToDoDetailBLL>();
        }
    }
}
