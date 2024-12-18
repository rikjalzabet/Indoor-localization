using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Interfaces
{
    public interface IAssetPositionHistoryRepository
    {
        IQueryable<AssetPositionHistory> GetAssetPositionHistory();
        IQueryable<AssetPositionHistory> GetAssetPositionHistoryByFloorMapId(int floorMapId);
        int AddAssetPositionHistory(AssetPositionHistory assetPositionHistory);
    }
}
