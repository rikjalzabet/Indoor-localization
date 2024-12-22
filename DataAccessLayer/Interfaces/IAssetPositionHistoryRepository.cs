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
        Task<List<AssetPositionHistory>> GetAssetPositionHistory();
        Task<List<AssetPositionHistory>> GetAssetPositionHistoryByFloorMapId(int floorMapId);
        Task<int> AddAssetPositionHistory(AssetPositionHistory assetPositionHistory);
    }
}
