using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Services
{
    public interface IFloorMapService
    {
        Task<List<FloorMap>> GetAllFloorMap();
        Task<FloorMap> GetFloorMapById(int id);
        Task<bool> AddFloorMap(FloorMap floorMap);
        Task<bool> UpdateFloorMap(FloorMap floorMap);
        Task<bool> DeleteFloorMap(int id);
    }
}
