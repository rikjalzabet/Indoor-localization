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
            bool su
            _assetRepository.AddAsset(asset);
        }

        public bool DeleteAsset(int id)
        {
            throw new NotImplementedException();
        }

        public Asset GetAssetById(int id)
        {
            throw new NotImplementedException();
        }

        public bool UpdateAsset(Asset asset)
        {
            throw new NotImplementedException();
        }
    }
}
