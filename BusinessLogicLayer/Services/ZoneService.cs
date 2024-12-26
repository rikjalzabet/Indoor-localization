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
    public class ZoneService : IZoneService
    {
        private readonly IZoneRepository _zoneRepository;
        public ZoneService(IZoneRepository zoneRepository)
        { 
            _zoneRepository = zoneRepository;
        }
        public async Task<List<Zone>> GetAllZones()
        {
            var queryableZones = await _zoneRepository.GetAllAsync();
            return queryableZones.ToList();
        }

        public async Task<Zone> GetZoneById(int id)
        {
            var zone = await _zoneRepository.GetByIdAsync(id);
            return zone;
        }

        public async Task<bool> AddZone(Zone zone)
        {
            int affectedRow = await _zoneRepository.AddAsync(zone);

            return affectedRow > 0;
        }

        public async Task<bool> DeleteZone(int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _zoneRepository.DeleteAsync(id);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
        public async Task<bool> UpdateZone(Zone zone, int id)
        {
            bool isSuccessful = false;
            int affectedRow = await _zoneRepository.UpdateZone(zone, id);

            isSuccessful = affectedRow > 0;
            return isSuccessful;
        }
    }
}
