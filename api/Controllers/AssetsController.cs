﻿using BusinessLogicLayer.Interfaces;
using EntityLayer.Entities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AssetsController : ControllerBase
    {
        private readonly IAssetService _assetService;
        public AssetsController(IAssetService assetService)
        {
            _assetService = assetService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAll()
        {
            var assets = await _assetService.GetAllAssets();
            if (assets != null)
            {
                return Ok(assets);
            }
            else
            {
                return BadRequest();
            }
            
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetById(int id)
        {
            var asset = await _assetService.GetAssetById(id);
            if (asset != null)
            {
                return Ok(asset);
            }
            else
            {
                return BadRequest();
            }

        }

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] Asset asset)
        {
            bool isAdded = false;
            if (asset != null)
            {
                isAdded = await _assetService.AddAsset(asset);
            }

            return isAdded ? Ok() : BadRequest();

        }



        [HttpPut("{id}")]
        public async Task<IActionResult> Update([FromBody] Asset asset, [FromRoute] int id)
        {
            Console.WriteLine("TESTIRAMO ID iz route: " + id);
            bool isUpdated = false;
            if (asset != null)
            {
                isUpdated = await _assetService.UpdateAsset(asset, id);
            }

            return isUpdated ? Ok() : BadRequest();

        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            bool isDeleted = false;
            if (id != null)
            {
                isDeleted = await _assetService.DeleteAsset(id);
            }
       
            return isDeleted ? Ok() : BadRequest();
        }
    }
}
