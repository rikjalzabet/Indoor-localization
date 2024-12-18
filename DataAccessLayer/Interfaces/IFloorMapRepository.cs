using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Interfaces
{
    public interface IFloorMapRepository
    {
        Task<List<FloorMap>> GetAllFloorMaps();
        Task<FloorMap> GetFloorMapById(int id);
        Task<int> AddFloorMap(FloorMap floorMap);
        Task<int> UpdateFloorMap(FloorMap floorMap, int id);
        Task<int> DeleteFloorMap(int id);
    }
}
