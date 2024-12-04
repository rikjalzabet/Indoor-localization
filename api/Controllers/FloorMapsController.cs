using BusinessLogicLayer.Services;
using EntityLayer.Entities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FloorMapsController : ControllerBase
    {
        private readonly IFloorMapService _floorMapService;
        public FloorMapsController(IFloorMapService floorMapService)
        {
            _floorMapService = floorMapService;
        }

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] FloorMap floorMap)
        {
            bool isAdded = false;
            if (floorMap != null)
            {
                isAdded = await _floorMapService.AddFloorMap(floorMap);
            }
            if (isAdded)
            {
                return Ok();
            }
            return BadRequest();
        }
    }
}
