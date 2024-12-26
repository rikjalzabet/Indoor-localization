using BusinessLogicLayer.Interfaces;
using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public class FloorMapService : IFloorMapService
    {
        private readonly IFloorMapRepository _floorMapRepository;
        public FloorMapService(IFloorMapRepository floorMapRepository)
        {
            _floorMapRepository = floorMapRepository;
        }
        public async Task<bool> AddFloorMap(FloorMap floorMap)
        {
            int affectedRow = await _floorMapRepository.AddAsync(floorMap);

            return affectedRow > 0;
        }

        public async Task<bool> DeleteFloorMap(int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _floorMapRepository.DeleteAsync(id);
            isSuccessful = affectedRow > 0;

            return isSuccessful;
        }

        public async Task<List<FloorMap>> GetAllFloorMaps()
        {
            var queryableFloorMaps = await _floorMapRepository.GetAllAsync();
            return queryableFloorMaps.ToList();
        }

        public async Task<FloorMap> GetFloorMapById(int id)
        {
            var existingFloorMap = await _floorMapRepository.GetByIdAsync(id);
            return existingFloorMap;
        }

        public async Task<bool> UpdateFloorMap(FloorMap floorMap, int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _floorMapRepository.UpdateFloorMap(floorMap, id);
            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
    }
}
