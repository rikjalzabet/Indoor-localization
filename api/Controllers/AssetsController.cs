using BusinessLogicLayer.Services;
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

        [HttpPost]
        public async Task<IActionResult> Add([FromBody] Asset asset)
        {
            bool isAdded = false;
            if (asset != null)
            {
                isAdded = await _assetService.AddAsset(asset);
            }
            if(isAdded)
            {
                return Ok();
            }
            return BadRequest();
        }
    }
}
