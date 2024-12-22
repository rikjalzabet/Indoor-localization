using BusinessLogicLayer.Interfaces;
using EntityLayer.DTOs;
using EntityLayer.Entities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;

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
        public async Task<IActionResult> Add([FromBody] ZoneDTO zoneDTO)
        {
            bool isAdded = false;

            var pointsJson = JsonDocument.Parse(JsonSerializer.Serialize(zoneDTO.Points));

            var zone = new Zone
            {
                Id = zoneDTO.Id,
                Name = zoneDTO.Name,
                Points = pointsJson
            };

            if (zone != null)
            {
                isAdded = await _zoneService.AddZone(zone);
            }

            return isAdded ? Ok() : BadRequest();

        }



        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromBody] Zone zone, [FromRoute] int id)
        {
            bool isUpdated = false;
            if (zone != null)
            {
                isUpdated = await _zoneService.UpdateZone(zone, id);
            }

            return isUpdated ? Ok() : BadRequest();

        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            bool isDeleted = false;
            if (id != null)
            {
                isDeleted = await _zoneService.DeleteZone(id);
            }
            
            return isDeleted ? Ok() : BadRequest();
        }
    }
}
