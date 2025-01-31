using BusinessLogicLayer.Interfaces;
using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public class AssetService : IAssetService
    {
        private readonly IAssetRepository _assetRepository;
        public AssetService(IAssetRepository assetRepository)
        {
            _assetRepository = assetRepository;
        }
        public async Task<List<Asset>> GetAllAssets()
        {
            var queryableAssets = await _assetRepository.GetAllAsync();
            return queryableAssets.ToList();
        }
        public async Task<bool> AddAsset(Asset asset)
        {
            int affectedRow = await _assetRepository.AddAsync(asset);

            return affectedRow > 0;
        }

        public async Task<bool> DeleteAsset(int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _assetRepository.DeleteAsync(id);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }

        public async Task<Asset> GetAssetById(int id)
        {
            var asset = await _assetRepository.GetByIdAsync(id);
            return asset;
        }

        public async Task<bool> UpdateAsset(Asset asset, int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _assetRepository.UpdateAsset(asset, id);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
        public async Task<bool> UpdateAssetPosition(AssetPositionHistory assetPositionHistory)
        {
            bool isSuccessful = false;
            int affectedRow = await _assetRepository.UpdateAssetPosition(assetPositionHistory);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
    }
}
