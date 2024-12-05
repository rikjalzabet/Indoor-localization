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

        public Task<bool> DeleteFloorMap(int id)
        {
            throw new NotImplementedException();
        }

        public Task<List<FloorMap>> GetAllFloorMap()
        {
            throw new NotImplementedException();
        }

        public Task<FloorMap> GetFloorMapById(int id)
        {
            throw new NotImplementedException();
        }

        public async Task<bool> UpdateFloorMap(FloorMap floorMap)
        {
            bool isSuccessful = false;
            int affectedRow = await _floorMapRepository.UpdateFloorMap(floorMap);
            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
    }
}
