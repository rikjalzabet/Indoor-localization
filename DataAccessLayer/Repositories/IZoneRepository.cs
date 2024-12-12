using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public interface IZoneRepository
    {
        Task<List<Zone>> GetAllZones();
        Task<Zone> GetZoneById(int id);
        Task<int> AddZone(Zone zone);
        Task<int> UpdateZone(Zone zone, int id);
        Task<int> DeleteZone(int id);
    }
}
