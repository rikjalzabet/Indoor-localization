using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.MockRepositories
{
    public class MockAssetPositionHistoryRepository : IRepository<AssetPositionHistory>
    {
        private readonly List<AssetPositionHistory> _assetPositionHistories = new()
        {
            new AssetPositionHistory
            {
                Id = 1,
                AssetId = 1,
                X = 10.5,
                Y = 20.5,
                DateTime = DateTime.UtcNow.AddMinutes(-30),
                FloorMapId = 1
            },
            new AssetPositionHistory
            {
                Id = 2,
                AssetId = 2,
                X = 15.5,
                Y = 25.5,
                DateTime = DateTime.UtcNow.AddMinutes(-20),
                FloorMapId = 1
            },
            new AssetPositionHistory
            {
                Id = 3,
                AssetId = 1,
                X = 11.0,
                Y = 21.0,
                DateTime = DateTime.UtcNow.AddMinutes(-10),
                FloorMapId = 1
            },
            new AssetPositionHistory
            {
                Id = 4,
                AssetId = 3,
                X = 5.5,
                Y = 10.5,
                DateTime = DateTime.UtcNow.AddHours(-1),
                FloorMapId = 2
            },
            new AssetPositionHistory
            {
                Id = 5,
                AssetId = 3,
                X = 6.0,
                Y = 11.0,
                DateTime = DateTime.UtcNow.AddMinutes(-5),
                FloorMapId = 2
            }
        };
        public async Task<int> AddAsync(AssetPositionHistory assetPositionHistory)
        {
            var existingAssetPositionHistory = _assetPositionHistories.FirstOrDefault(a => a.Id == assetPositionHistory.Id);
            if (existingAssetPositionHistory == null)
            {
                _assetPositionHistories.Add(assetPositionHistory);
                return 1;
            }
            return 0;
        }

        public Task<int> DeleteAsync(int id)
        {
            throw new NotImplementedException();
        }

        public async Task<IEnumerable<AssetPositionHistory>> GetAllAsync()
        {
            return await Task.FromResult(_assetPositionHistories);
        }

        public async Task<List<AssetPositionHistory>> GetAssetPositionHistoryByFloorMapId(int floorMapId)
        {

            return await Task.FromResult(_assetPositionHistories.Where(a => a.FloorMapId == floorMapId).ToList());
        }

        public Task<AssetPositionHistory> GetByIdAsync(int id)
        {
            throw new NotImplementedException();
        }
    }
}
