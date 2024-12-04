using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Repositories
{
    public interface IFloorMapRepository
    {
        Task<List<FloorMap>> GetAllFloorMaps();
        Task<FloorMap> GetFloorMapById(int id);
        Task<int> AddFloorMap(Asset asset);
        Task<int> UpdateFloorMap(FloorMap floorMap);
        Task<int> DeleteFloorMap(int id);
    }
}
