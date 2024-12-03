using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public interface IZoneService
    {
        Task<List<Zone>> GetAllZones();
        Task<Zone> GetZoneById(int id);
        Task<bool> AddZone(Zone zone);
        Task<bool> UpdateZone(Zone zone);
        Task<bool> DeleteZone(int id);
    }
}
