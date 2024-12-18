using DataAccessLayer.Interfaces;
using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public class FloorMapRepository : Repository<FloorMap>, IFloorMapRepository
    {
        public FloorMapRepository(AppDbContext context) : base(context)
        {

        }

        public async Task<int> UpdateFloorMap(FloorMap floorMap, int id)
        {
            var existingFloorMap = await Entities.FindAsync(floorMap);

            if (existingFloorMap != null)
            {
                existingFloorMap.Name = floorMap.Name;
                existingFloorMap.Image = floorMap.Image;
            }
            return await _context.SaveChangesAsync();

        }
    }
}
