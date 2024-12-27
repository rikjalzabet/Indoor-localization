using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Interfaces
{
    public interface IAssetZoneHistoryRepository : IRepository<AssetZoneHistory>
    {
        Task UpdateZoneHistory(AssetZoneHistory assetZoneHistory);
        Task<AssetZoneHistory> GetLatesByAssetId(int assetId);
    }
}
