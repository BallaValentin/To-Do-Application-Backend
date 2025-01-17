using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using ToDoApplication.API.DTOs.Get.ToDo;
using ToDoApplication.API.DTOs.Post.ToDo;
using ToDoApplication.API.DTOs.Put.ToDo;
using ToDoApplication.BLL.BLLs.Post.ToDo;
using ToDoApplication.BLL.BLLs.Put.ToDo;
using ToDoApplication.BLL.Contexts;
using ToDoApplication.BLL.Exceptions;
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
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<GetToDoDTO>> CreateToDo([FromBody] PostToDoDTO toDoDto)
        {
            try
            {
                var toDoBll = Mapper.Map<PostToDoBLL>(toDoDto);
                var createdToDoBll = await Manager.CreateToDoAsync(toDoBll);
                var createdToDoDto = Mapper.Map<GetToDoDTO>(createdToDoBll);
                return createdToDoDto;
            }
            catch (Exception ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status500InternalServerError
                };
            }
        }

        [HttpGet]
        [ProducesResponseType<List<GetToDoDTO>>(StatusCodes.Status200OK)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<List<GetToDoDTO>>> GetToDos()
        {
            try
            {
                var toDosBll = await Manager.GetToDosAsync();
                var toDosDto = Mapper.Map<List<GetToDoDTO>>(toDosBll);
                return toDosDto;
            }
            catch (Exception ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status500InternalServerError
                };
            }
        }

        [HttpGet("{id}")]
        [ProducesResponseType<GetToDoDTO>(StatusCodes.Status200OK)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status404NotFound)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<GetToDoDTO>> GetToDoById(int id)
        {
            try
            {
                var toDoBll = await Manager.GetToDoByIdAsync(id);
                var toDoDto = Mapper.Map<GetToDoDTO>(toDoBll);
                return toDoDto;
            } 
            catch (NotFoundException ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status404NotFound
                };
            }
            catch (Exception ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status500InternalServerError
                };
            }
        }

        [HttpPut("{id}")]
        [ProducesResponseType<GetToDoDTO>(StatusCodes.Status200OK)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status404NotFound)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<GetToDoDTO>> UpdateToDoById([FromBody] PutToDoDTO toDoDTO, int id)
        {
            try
            {
                var toDoBll = Mapper.Map<PutToDoBLL>(toDoDTO);
                var updatedToDoBll = await Manager.UpdateToDoByIdAsync(toDoBll, id);
                var updatedToDoDto = Mapper.Map<GetToDoDTO>(updatedToDoBll);
                return updatedToDoDto;
            }
            catch (NotFoundException ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status404NotFound
                };
            }
            catch (Exception ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status500InternalServerError
                };
            }
        }

        [HttpDelete("{id}")]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status200OK)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status404NotFound)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult> DeleteToDoById(int id)
        {
            try
            {
                var response = await Manager.DeleteToDoByIdAsync(id);
                return response;
            }
            catch (NotFoundException ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status404NotFound
                };
            }
            catch (Exception ex)
            {
                return new ObjectResult($"Error: {ex.Message}")
                {
                    StatusCode = StatusCodes.Status500InternalServerError
                };
            }
        }
    }
}
