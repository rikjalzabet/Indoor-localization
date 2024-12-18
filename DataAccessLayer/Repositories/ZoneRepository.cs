using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class ZoneRepository //: IZoneRepository
    {
        /*protected readonly AppDbContext Context;
        public DbSet<Zone> Entities { get; set; }

        public ZoneRepository(AppDbContext context)
        {
            Context = context;
            Entities = Context.Set<Zone>();
        }

        public async Task<List<Zone>> GetAllZones()
        {
            var sql = await from e in Entities select e;
            return sql;
        }

        public async Task<Zone> GetZoneById(int id)
        {
            var sql = await Entities.FirstOrDefault(z => z.Id == id);
            return sql;
        }
        public async Task<int> AddZone(Zone zone)
        {
            await Entities.Add(zone);
            await Context.SaveChanges();
            return 1;
        }
        public async Task<int> UpdateZone(Zone zone, int id)
        {
            Zone existingZone = await Entities.FirstOrDefault(z => z.Id == id);

            if (existingZone == null)
            {
                return 0;
            }
            existingZone.Name = zone.Name;
            existingZone.Points = zone.Points;

            await Context.SaveChanges();

            return 1;
        }
        public async Task<int> DeleteZone(int id)
        {
            Zone existingZone = await Entities.FirstOrDefault(z => z.Id == id);
            if (existingZone == null)
            {
                return 0;
            }
            await Entities.Attach(existingZone);
            await Entities.Remove(existingZone);
            await Context.SaveChanges();

            return 1;
        } 
        */
    }
}
