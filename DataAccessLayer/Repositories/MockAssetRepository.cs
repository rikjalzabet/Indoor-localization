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

        // ovaj repozitorij bude zamijenjen pravim repozitorijem nakon kreiranja EFC-a prema bazi

        private readonly List<Asset> _assets = new()
        {
                new Asset { Id = 1, Name = "Asset 1", X = 10.5, Y = 20.5, LastSync = DateTime.UtcNow, FloorMapId = 1, Active = true },
                new Asset { Id = 2, Name = "Asset 2", X = 15.5, Y = 25.5, LastSync = DateTime.UtcNow.AddMinutes(-30), FloorMapId = 1, Active = true },
                new Asset { Id = 3, Name = "Asset 3", X = 5.5, Y = 10.5, LastSync = DateTime.UtcNow.AddHours(-1), FloorMapId = 2, Active = false }
        };

        // u pravom repozitoriju sa EFC ide public async, i await umjesto Task.FromResult..
        public async Task<List<Asset>> GetAllAssets()
        {
            return await Task.FromResult(_assets);
        }
        public async Task<int> AddAsset(Asset asset)
        {
            _assets.Add(asset);
            return 1;
        }

        public int DeleteAsset(int id)
        {
            throw new NotImplementedException();
        }

        public async Task<Asset> GetAssetById(int id)
        {
            return await Task.FromResult(_assets.FirstOrDefault(a => a.Id == id));
        }

        public int UpdateAsset(Asset asset)
        {
            throw new NotImplementedException();
        }
    }
}
