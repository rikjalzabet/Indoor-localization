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
        public List<Asset> GetAllAssets()
        {
            return _assetRepository.GetAllAssets().ToList();
        }
        public bool AddAsset(Asset asset)
        {
            throw new NotImplementedException();
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
