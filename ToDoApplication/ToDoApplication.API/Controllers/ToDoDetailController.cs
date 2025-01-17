using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ToDoApplication.BLL.Managers;

namespace ToDoApplication.API.Controllers
{
    [ApiController]
    [Route("/api/todos")]
    [Produces("application/json")]
    public class ToDoDetailController : ControllerBase
    {
        private readonly ToDoDetailManager Manager;
        public IMapper Mapper { get; set; }

        public ToDoDetailController(ToDoDetailManager manager, IMapper mapper)
        {
            Manager = manager;
            Mapper = mapper;
        }
    }
}
