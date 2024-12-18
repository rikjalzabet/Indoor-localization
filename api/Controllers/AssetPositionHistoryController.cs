using BusinessLogicLayer.Interfaces;
using BusinessLogicLayer.Services;
using EntityLayer.Entities;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AssetPositionHistoryController : ControllerBase
    {
        private readonly IAssetPositionHistoryService _assetPositionHistoryService;
        public AssetPositionHistoryController(IAssetPositionHistoryService assetPositionHistoryService)
        {
            _assetPositionHistoryService = assetPositionHistoryService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAllAssetPositionHistory()
        {
            var assetPositionHistories = await _assetPositionHistoryService.GetAssetPositionHistory();
            if (assetPositionHistories != null)
            {
                return Ok(assetPositionHistories);
            }
            else
            {
                return BadRequest();
            }

        }

        [HttpGet("{floorMapId}")]
        public async Task<IActionResult> GetAssetPositionHistoriesByFloorMapId(int floorMapId)
        {
            var assetPositionHistoriesByFloorMapId = await _assetPositionHistoryService.GetAssetPositionHistoryByFloorMapId(floorMapId);
            if (assetPositionHistoriesByFloorMapId != null)
            {
                return Ok(assetPositionHistoriesByFloorMapId);
            }
            else
            {
                return BadRequest();
            }

        }

        [HttpPost]
        public async Task<IActionResult> AddAssetPositionHistory([FromBody] AssetPositionHistory assetPositionHistory)
        {
            bool isAdded = false;
            if (assetPositionHistory != null)
            {
                isAdded = await _assetPositionHistoryService.AddAssetPositionHistory(assetPositionHistory);
            }

            return isAdded ? Ok() : BadRequest();

        }
    }
}
