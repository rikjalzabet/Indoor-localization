using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Interfaces
{
    public interface IAssetRepository : IRepository<Asset>
    {
        Task<int> UpdateAsset(Asset asset, int id);
        Task<int> UpdateAssetPosition(AssetPositionHistory assetPositionHistory);

    }
}
