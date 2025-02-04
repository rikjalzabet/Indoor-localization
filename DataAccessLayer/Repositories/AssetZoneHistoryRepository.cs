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

        public async Task<IEnumerable<AssetZoneHistory>> GetAssetZoneHistoryByAssetId(int assetId)
        {
            return await Entities.Include("Asset").Include("Zone").Where(a => a.AssetId == assetId).ToListAsync();
        }

        public async Task<IEnumerable<AssetZoneHistory>> GetAssetZoneHistoryByZoneId(int zoneId)
        {
            return await Entities.Include("Asset").Include("Zone").Where(a => a.ZoneId == zoneId).ToListAsync();
        }

        public async Task<AssetZoneHistory> GetLatesByAssetId(int assetId)
        {
            return await Entities.Where(a => a.AssetId == assetId).OrderByDescending(a => a.EnterDateTime).FirstOrDefaultAsync();
        }

        public async Task<int> UpdateZoneHistory(AssetZoneHistory assetZoneHistory)
        {
            var existingAssetZoneHistory = await Entities.FindAsync(assetZoneHistory.Id);

            if (existingAssetZoneHistory != null)
            {
                existingAssetZoneHistory.Id = assetZoneHistory.Id;
                existingAssetZoneHistory.AssetId = assetZoneHistory.AssetId;
                existingAssetZoneHistory.ZoneId = assetZoneHistory.ZoneId;
                existingAssetZoneHistory.EnterDateTime = assetZoneHistory.EnterDateTime;
                existingAssetZoneHistory.ExitDateTime = assetZoneHistory.ExitDateTime;
                existingAssetZoneHistory.RetentionTime = assetZoneHistory.RetentionTime;
            }

            return await _context.SaveChangesAsync();
        }
    }
}
