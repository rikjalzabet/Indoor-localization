using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using EntityLayer.Interfaces;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class AssetPositionHistoryRepository : Repository<AssetPositionHistory>, IAssetPositionHistoryRepository
    {

        public AssetPositionHistoryRepository(AppDbContext context) : base(context)
        {

        }
        public async Task<IEnumerable<AssetPositionHistory>> GetAssetPositionHistoryByFloorMapId(int floorMapId)
        {
            return await Entities.Include("Asset").Include("FloorMap").ToListAsync();
        }
    }
}
