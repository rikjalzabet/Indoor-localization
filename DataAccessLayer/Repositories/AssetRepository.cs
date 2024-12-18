using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class AssetRepository //:IAssetRepository
    {
        /*
        protected readonly AppDbContext Context;
        public DbSet<Asset> Entities { get; set; }

        public AssetRepository(AppDbContext context)
        {
            Context = context;
            Entities = Context.Set<Asset>();
        }

        public async Task<List<Asset>> GetAllAssets()
        {
            var sql = from e in Entities.Include("FloorMap") select e;
            return sql;
        }

        public async Task<Asset> GetAssetById(int id)
        {
            var sql = Entities.FirstOrDefault(a => a.Id == id);
            return sql;
        }

        public async Task<int> AddAsset(Asset asset)
        {
            Entities.Add(asset);
            Context.SaveChanges();
            return 1;
        }

        public async Task<int> UpdateAsset(Asset asset, int id)
        {
            Asset existingAsset = Entities.FirstOrDefault(a => a.Id == id);

            if (existingAsset == null)
            {
                return 0;
            }
            existingAsset.X = asset.X;
            existingAsset.Y = asset.Y;
            existingAsset.Active = asset.Active;
            existingAsset.LastSync = asset.LastSync;
            existingAsset.FloorMapId  = asset.FloorMapId;

            Context.SaveChanges();

            return 1;
        }

        public async Task<int> DeleteAsset(int id)
        {
            Asset existingAsset = Entities.FirstOrDefault(a => a.Id == id);
            if (existingAsset == null)
            {
                return 0;
            }
            Entities.Attach(existingAsset);
            Entities.Remove(existingAsset);
            Context.SaveChanges();


            return 1;
        }
        */
    }
}
