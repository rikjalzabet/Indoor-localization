using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public interface IAssetPositionHistoryService
    {
        Task<List<AssetPositionHistory>> GetAssetPositionHistory();
        Task<List<AssetPositionHistory>> GetAssetPositionHistoryByFloorMapId(int floorMapId);
        Task<bool> AddAssetPositionHistory(AssetPositionHistory assetPositionHistory);
    }
}
