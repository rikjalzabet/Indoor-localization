using DataAccessLayer.Repositories;
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
            bool isSuccessful = false;
            int affectedRow = await _floorMapRepository.AddFloorMap(floorMap);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }

        public async Task<bool> DeleteFloorMap(int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _floorMapRepository.DeleteFloorMap(id);
            isSuccessful = affectedRow > 0;

            return isSuccessful;
        }

        public async Task<List<FloorMap>> GetAllFloorMaps()
        {
            var queryableFloorMaps = await _floorMapRepository.GetAllFloorMaps();
            return queryableFloorMaps;
        }

        public async Task<FloorMap> GetFloorMapById(int id)
        {
            var existingFloorMap = await _floorMapRepository.GetFloorMapById(id);
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
