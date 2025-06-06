﻿using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.MockRepositories
{
    public class MockFloorMapRepository : IRepository<FloorMap>
    {
        private readonly List<FloorMap> _floorMaps = new()
        {
            new FloorMap {Id = 1, Name = "FloorMap944", Image = "https://picsum.photos/id/944/500/500"},
            new FloorMap {Id = 2, Name = "FloorMap343", Image = "https://picsum.photos/id/343/500/500"},
            new FloorMap {Id = 3, Name = "FloorMap69", Image = "https://picsum.photos/id/69/500/500"},
            new FloorMap {Id = 4, Name = "FloorMap879", Image = "https://picsum.photos/id/879/500/500"},
            new FloorMap {Id = 5, Name = "FloorMap333", Image = "https://picsum.photos/id/333/500/500"}
        };
        public async Task<int> AddAsync(FloorMap floorMap)
        {
            var existingFloorMap = _floorMaps.FirstOrDefault(f => f.Id == floorMap.Id);
            if (existingFloorMap == null)
            {
                _floorMaps.Add(floorMap);
                return 1;
            }
            return 0;
        }

        public async Task<int> DeleteAsync(int id)
        {
            var existingFloorMap = _floorMaps.FirstOrDefault(f => f.Id == id);
            if (existingFloorMap != null)
            {
                _floorMaps.Remove(existingFloorMap);
                return 1;
            }
            return 0;
        }

        public async Task<IEnumerable<FloorMap>> GetAllAsync()
        {
            return await Task.FromResult(_floorMaps);
        }

        public async Task<FloorMap> GetByIdAsync(int id)
        {
            return await Task.FromResult(_floorMaps.FirstOrDefault(f => f.Id == id));
        }

        public async Task<int> UpdateFloorMap(FloorMap floorMap, int id)
        {
            var existingFloorMap = _floorMaps.FirstOrDefault(f => f.Id == id);
            if (existingFloorMap != null)
            {
                existingFloorMap.Name = floorMap.Name;
                existingFloorMap.Image = floorMap.Image;

                return 1;
            }
            else
                return 0;
        }
    }
}
