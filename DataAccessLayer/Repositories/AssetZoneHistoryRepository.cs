using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
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

    }
}
