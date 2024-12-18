using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.MockRepositories
{
    public class MockAssetRepository : IRepository<Asset>
    {

        // ovaj repozitorij bude zamijenjen pravim repozitorijem nakon kreiranja EFC-a prema bazi
        // tu je simulirano asinkrono programiranje jer nemamo baze...

        private readonly List<Asset> _assets = new()
        {
                new Asset { Id = 1, Name = "Asset 1", X = 10.5, Y = 20.5, LastSync = DateTime.UtcNow, FloorMapId = 1, Active = true },
                new Asset { Id = 2, Name = "Asset 2", X = 15.5, Y = 25.5, LastSync = DateTime.UtcNow.AddMinutes(-30), FloorMapId = 1, Active = true },
                new Asset { Id = 3, Name = "Asset 3", X = 5.5, Y = 10.5, LastSync = DateTime.UtcNow.AddHours(-1), FloorMapId = 2, Active = false }
        };

        // u pravom repozitoriju sa EFC ide public async, i await umjesto Task.FromResult..
        public async Task<IEnumerable<Asset>> GetAllAsync()
        {
            return await Task.FromResult(_assets);
        }
        public async Task<int> AddAsync(Asset asset)
        {
            var existingAsset = _assets.FirstOrDefault(a => a.Id == asset.Id);
            if (existingAsset == null)
            {
                _assets.Add(asset);
                return 1;
            }
            return 0;
        }

        public async Task<int> DeleteAsync(int id)
        {
            var existingAsset = _assets.FirstOrDefault(a => a.Id == id);
            if (existingAsset == null)
                return 0;
            _assets.Remove(existingAsset);
            return 1;
        }

        public async Task<Asset> GetByIdAsync(int id)
        {
            return await Task.FromResult(_assets.FirstOrDefault(a => a.Id == id));
        }

        public async Task<int> UpdateAsset(Asset asset, int id)
        {
            var existingAsset = _assets.FirstOrDefault(a => a.Id == id);

            if (existingAsset == null)
                return 0;

            existingAsset.Name = asset.Name;
            existingAsset.X = asset.X;
            existingAsset.Y = asset.Y;
            existingAsset.Active = asset.Active;
            existingAsset.FloorMapId = asset.FloorMapId;

            return 1;

        }
    }
}
