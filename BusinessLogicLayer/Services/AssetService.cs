using DataAccessLayer.Repositories;
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
            var queryableAssets = await _assetRepository.GetAllAssets();
            return queryableAssets.ToList();
        }
        public async Task<bool> AddAsset(Asset asset)
        {
            bool isSuccessful = false;
            int affectedRow = await _assetRepository.AddAsset(asset);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }

        public bool DeleteAsset(int id)
        {
            throw new NotImplementedException();
        }

        public async Task<Asset> GetAssetById(int id)
        {
            var asset = await _assetRepository.GetAssetById(id);
            return asset;
        }

        public async Task<bool> UpdateAsset(Asset asset)
        {
            bool isSuccessful = false;
            int affectedRow = await _assetRepository.UpdateAsset(asset);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
    }
}
