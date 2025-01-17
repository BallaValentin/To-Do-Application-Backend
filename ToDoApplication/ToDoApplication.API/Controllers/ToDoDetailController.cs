using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using ToDoApplication.API.DTOs.Get.ToDoDetail;
using ToDoApplication.API.DTOs.Post.ToDoDetail;
using ToDoApplication.BLL.BLLs.Post.ToDoDetail;
using ToDoApplication.BLL.Contexts;
using ToDoApplication.BLL.Exceptions;
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

        public ToDoDetailController(ToDoDbContext dbContext, IMapper mapper)
        {
            Mapper = mapper;
            Manager = new ToDoDetailManager(dbContext, mapper);
        }

        [HttpPost("{todoId}/details")]
        [ProducesResponseType<GetToDoDetailDTO>(StatusCodes.Status201Created)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status404NotFound)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<GetToDoDetailDTO>> CreateToDoDetail([FromBody] PostToDoDetailDTO toDoDetailDTO, int todoId)
        {
            try
            {
                var toDoDetailBll = Mapper.Map<PostToDoDetailBLL>(toDoDetailDTO);
                var newToDoDetailBll = await Manager.CreateToDoDetailAsync(toDoDetailBll, todoId);
                var newToDoDetailDto = Mapper.Map<GetToDoDetailDTO>(newToDoDetailBll);
                return newToDoDetailDto;
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

        [HttpGet("{todoId}/details")]
        [ProducesResponseType<List<GetToDoDetailDTO>>(StatusCodes.Status200OK)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status404NotFound)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<List<GetToDoDetailDTO>>> GetToDoDetails(int todoId)
        {
            try
            {
                var toDoDetailsBll = await Manager.GetToDoDetailsAsync(todoId);
                var toDoDetailsDto = Mapper.Map<List<GetToDoDetailDTO>>(toDoDetailsBll);
                return toDoDetailsDto;
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

        [HttpDelete("{todoId}/details/{todoDetailId}")]
        [ProducesResponseType<List<GetToDoDetailDTO>>(StatusCodes.Status200OK)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status404NotFound)]
        [ProducesResponseType<ObjectResult>(StatusCodes.Status500InternalServerError)]
        public async Task<ActionResult<List<GetToDoDetailDTO>>> DeleteToDoDetailById(int todoId, int todoDetailId)
        {
            try
            {
                var response = await Manager.DeleteToDoDetailByIdAsync(todoId, todoDetailId);
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
