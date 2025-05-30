﻿using BusinessLogicLayer.Interfaces;
using BusinessLogicLayer.Services;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AssetZoneHistoryController : ControllerBase
    {
        private readonly IAssetZoneHistoryService _assetZoneHistoryService;
        public AssetZoneHistoryController(IAssetZoneHistoryService assetZoneHistoryService)
        {
            _assetZoneHistoryService = assetZoneHistoryService;
        }

        [HttpGet]
        public async Task<IActionResult> GetAllAssetPositionHistory()
        {
            var assetZoneHistories = await _assetZoneHistoryService.GetAssetZoneHistory();
            if (assetZoneHistories != null)
            {
                return Ok(assetZoneHistories);
            }
            else
            {
                return BadRequest();
            }
        }

        [HttpGet("{assetId}")]
        public async Task<IActionResult> GetAssetZoneHistoriesByAssetId(int assetId)
        {
            var assetZoneHistoriesByAssetId = await _assetZoneHistoryService.GetAssetZoneHistoryByAssetId(assetId);
            if (assetZoneHistoriesByAssetId != null)
            {
                return Ok(assetZoneHistoriesByAssetId);
            }
            else
            {
                return BadRequest();
            }
        }
    }
}
