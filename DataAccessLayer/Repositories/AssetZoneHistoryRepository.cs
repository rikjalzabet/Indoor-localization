using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class AssetZoneHistoryRepository : Repository<AssetZoneHistory>, IAssetZoneHistoryRepository
    {
        public AssetZoneHistoryRepository(AppDbContext context) : base(context)
        {

        }

        public async Task<AssetZoneHistory> GetLatesByAssetId(int assetId)
        {
            return await Entities.Where(a => a.AssetId == assetId).OrderByDescending(a => a.EnterDateTime).FirstOrDefaultAsync();
        }

        public async Task UpdateZoneHistory(AssetZoneHistory assetZoneHistory)
        {
            
        }
    }
}
