using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public class AssetPositionHistoryService : IAssetPositionHistoryService
    {
        private readonly IAssetPositionHistoryRepository _assetPositionHistoryRepository;
        public AssetPositionHistoryService(IAssetPositionHistoryRepository assetPositionHistoryRepository)
        {
            _assetPositionHistoryRepository = assetPositionHistoryRepository;
        }

        public async Task<bool> AddAssetPositionHistory(AssetPositionHistory assetPositionHistory)
        {
            bool isSuccessful = false;
            int affectedRow = await _assetPositionHistoryRepository.AddAssetPositionHistory(assetPositionHistory);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }

        public async Task<List<AssetPositionHistory>> GetAssetPositionHistory()
        {
            var assetPositionHistories = await _assetPositionHistoryRepository.GetAssetPositionHistory();
            return assetPositionHistories;
        }

        public async Task<List<AssetPositionHistory>> GetAssetPositionHistoryByFloorMapId(int floorMapId)
        {
            var assetPositionHistoriesByFloorMapId = await _assetPositionHistoryRepository.GetAssetPositionHistoryByFloorMapId(floorMapId);
            return assetPositionHistoriesByFloorMapId;
        }
    }
}
