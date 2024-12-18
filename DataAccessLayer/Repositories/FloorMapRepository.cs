using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class FloorMapRepository//:IFloorMapRepository
    {
        /*protected readonly AppDbContext Context;
        public DbSet<FloorMap> Entities { get; set; }

        public FloorMapRepository(AppDbContext context)
        {
            Context = context;
            Entities = Context.Set<FloorMap>();
        }

        public async Task<List<FloorMap>> GetAllFloorMaps()
        {
            var sql = from e in Entities select e;
            if(sql != null)
            {
                return sql;
            }
            else
            {
                return null;
            }
        }

        public async Task<FloorMap> GetFloorMapById(int id)
        {
            var sql = await Entities.FirstOrDefault(fm => fm.Id == id);
            return sql;
        }

        public async Task<int> AddFloorMap(FloorMap floorMap)
        {
            await Entities.Add(floorMap);
            await Context.SaveChanges();
            return 1;
        }

        public async Task<int> UpdateFloorMap(FloorMap floorMap, int id)
        {
            FloorMap existingFloorMap = await Entities.FirstOrDefault(fm => fm.Id == id);

            if (existingFloorMap == null)
            {
                return 0;
            }
            existingFloorMap.Name = floorMap.Name;
            existingFloorMap.Image = floorMap.Image;

            await Context.SaveChanges();

            return 1;
        }

        public async Task<int> DeleteFloorMap(int id)
        {
            FloorMap existingFloorMap = await Entities.FirstOrDefault(fm => fm.Id == id);
            if (existingFloorMap == null)
            {
                return 0;
            }
            await Entities.Attach(existingFloorMap);
            await Entities.Remove(existingFloorMap);
            await Context.SaveChanges();

            return 1;
        }
        */
    }
}
