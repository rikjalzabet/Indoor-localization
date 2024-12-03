using BusinessLogicLayer.Services;
using EntityLayer.Entities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ZonesController : ControllerBase
    {
        private readonly IZoneService _zoneService;
        public ZonesController(IZoneService zoneService)
        {
            _zoneService = zoneService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            var zones = await _zoneService.GetAllZones(); 
            if (zones != null)
            {
                return Ok(zones);
            }
            else
            {
                return BadRequest();
            }
        }
        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var zone = await _zoneService.GetZoneById(id);
            if (zone != null)
            {
                return Ok(zone);
            }
            else
            {
                return BadRequest();
            }

        }

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] Zone zone)
        {
            bool isAdded = false;
            if (zone != null)
            {
                isAdded = await _zoneService.AddZone(zone);
            }
            if (isAdded)
            {
                return Ok();
            }
            return BadRequest();
        }



        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromBody] Zone zone)
        {
            bool isUpdated = false;
            if (zone != null)
            {
                isUpdated = await _zoneService.UpdateZone(zone);
            }
            if (isUpdated)
            {
                return Ok();
            }
            return BadRequest();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            bool isDeleted = false;
            if (id != null)
            {
                isDeleted = await _zoneService.DeleteZone(id);
            }
            if (isDeleted)
            {
                return Ok();
            }
            return BadRequest();
        }
    }
}
