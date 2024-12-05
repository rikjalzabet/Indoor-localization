using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public interface IAssetRepository
    {
        Task<List<Asset>> GetAllAssets();
        Task<Asset> GetAssetById(int id);
        Task<int> AddAsset(Asset asset);
        Task<int> UpdateAsset(Asset asset, int id);
        Task<int> DeleteAsset(int id);
    }
}
