using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class MockAssetRepository : IAssetRepository
    {
        public int AddAsset(Asset asset)
        {
            throw new NotImplementedException();
        }

        public int DeleteAsset(int id)
        {
            throw new NotImplementedException();
        }

        public IQueryable<Asset> GetAllAssets()
        {
            throw new NotImplementedException();
        }

        public Asset GetAssetById(int id)
        {
            throw new NotImplementedException();
        }

        public int UpdateAsset(Asset asset)
        {
            throw new NotImplementedException();
        }
    }
}
