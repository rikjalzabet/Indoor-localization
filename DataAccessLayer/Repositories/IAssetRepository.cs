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
        Asset GetAssetById(int id);
        Task<int> AddAsset(Asset asset);
        int UpdateAsset(Asset asset);
        int DeleteAsset(int id);
    }
}
