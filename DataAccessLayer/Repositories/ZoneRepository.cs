using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using EntityLayer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class ZoneRepository : Repository<Zone>, IZoneRepository
    {
        public ZoneRepository(AppDbContext context) : base(context)
        {

        }
        public async Task<int> UpdateZone(Zone zone, int id)
        {
            var existingZone = await Entities.FindAsync(zone);

            if (existingZone != null)
            {
                existingZone.Name = zone.Name;
                existingZone.Points = zone.Points;
            }

            return await _context.SaveChangesAsync();

        }
    }
}
