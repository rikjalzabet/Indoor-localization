﻿using BusinessLogicLayer.Interfaces;
using EntityLayer.DTOs;
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

        [HttpGet]
        public async Task<IActionResult> GetAllFloorMaps()
        {
            var floorMaps = await _floorMapService.GetAllFloorMaps();
            if (floorMaps != null)
            {
                return Ok(floorMaps);
            }
            else
                return BadRequest();
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var existingFloorMap = await _floorMapService.GetFloorMapById(id);
            if (existingFloorMap != null)
            {
                return Ok(existingFloorMap);
            }
            else
                return BadRequest();
        }

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] FloorMapDTO floorMapDTO)
        {
            bool isAdded = false;
            var floorMap = new FloorMap
            {
                Name = floorMapDTO.Name,
                Image = floorMapDTO.Image
            };

            if (floorMap != null)
            {
                isAdded = await _floorMapService.AddFloorMap(floorMap);
            }

            return isAdded ? Ok() : BadRequest();

        }

        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromBody] FloorMapDTO floorMapDTO, [FromRoute] int id)
        {
            bool isUpdated = false;
            var floorMap = new FloorMap
            {
                Name = floorMapDTO.Name,
                Image = floorMapDTO.Image
            };

            if (floorMap != null)
            {
                isUpdated = await _floorMapService.UpdateFloorMap(floorMap, id);
            }

            return isUpdated ? Ok() : BadRequest();
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            bool isDeleted = false;
            if (id != null)
            {
                isDeleted = await _floorMapService.DeleteFloorMap(id);
            }

            return isDeleted ? Ok() : BadRequest();
        }
    }
}
