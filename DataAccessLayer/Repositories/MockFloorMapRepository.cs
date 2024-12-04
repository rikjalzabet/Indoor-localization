using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class MockFloorMapRepository : IFloorMapRepository
    {
        private readonly List<FloorMap> _floorMaps = new()
        {
            new FloorMap { 1, 'FloorMap944', 'https://picsum.photos/id/944/500/500'},
            new FloorMap { 2, 'FloorMap343', 'https://picsum.photos/id/343/500/500'},
            new FloorMap {3, 'FloorMap69', 'https://picsum.photos/id/69/500/500'},
            new FloorMap {4, 'FloorMap879', 'https://picsum.photos/id/879/500/500'},
            new FloorMap{5, 'FloorMap333', 'https://picsum.photos/id/333/500/500'}
        };
        public async Task<int> AddFloorMap(FloorMap floorMap)
        {
            var existingFloorMap = _floorMaps.FirstOrDefault(f => f.Id == floorMap.Id);
            if (existingFloorMap == null)
            {
                _floorMaps.Add(floorMap);
                return 1;
            }
            return 0;
        }

        public Task<int> DeleteFloorMap(int id)
        {
            throw new NotImplementedException();
        }

        public Task<List<FloorMap>> GetAllFloorMaps()
        {
            throw new NotImplementedException();
        }

        public Task<FloorMap> GetFloorMapById(int id)
        {
            throw new NotImplementedException();
        }

        public Task<int> UpdateFloorMap(FloorMap floorMap)
        {
            throw new NotImplementedException();
        }
    }
}
