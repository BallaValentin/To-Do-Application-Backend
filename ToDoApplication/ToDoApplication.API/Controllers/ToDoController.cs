using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using ToDoApplication.API.DTOs.Get.ToDo;
using ToDoApplication.API.DTOs.Post.ToDo;
using ToDoApplication.BLL.BLLs.Post.ToDo;
using ToDoApplication.BLL.Contexts;
using ToDoApplication.BLL.Managers;

namespace ToDoApplication.API.Controllers
{
    [ApiController]
    [Route("/api/todos")]
    [Produces("application/json")]
    public class ToDoController : ControllerBase
    {
        private readonly ToDoManager Manager;
        public IMapper Mapper;

        public ToDoController(ToDoDbContext context, IMapper mapper)
        {
            Mapper = mapper;
            Manager = new ToDoManager(context, mapper);
        }

        [HttpPost]
        [ProducesResponseType<GetToDoDTO>(StatusCodes.Status201Created)]
        public async Task<ActionResult<GetToDoDTO>> CreateToDo([FromBody] PostToDoDTO toDoDto)
        {
            var toDoBll = Mapper.Map<PostToDoBLL>(toDoDto);
            var createdToDoBll = await Manager.CreateToDoAsync(toDoBll);
            var createdToDoDto = Mapper.Map<GetToDoDTO>(createdToDoBll);
            return createdToDoDto;
        }

        [HttpGet]
        [ProducesResponseType<List<GetToDoDTO>>(StatusCodes.Status200OK)]
        public async Task<ActionResult<List<GetToDoDTO>>> GetToDos()
        {
            var toDosBll = await Manager.GetToDosAsync();
            var toDosDto = Mapper.Map<List<GetToDoDTO>>(toDosBll);
            return toDosDto;
        }
    }
}
