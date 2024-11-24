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
        Task<IQueryable<Asset>> GetAllAssets();
        Asset GetAssetById(int id);
        int AddAsset(Asset asset);
        int UpdateAsset(Asset asset);
        int DeleteAsset(int id);
    }
}
