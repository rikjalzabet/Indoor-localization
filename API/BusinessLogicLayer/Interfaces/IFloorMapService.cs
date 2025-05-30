﻿using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Interfaces
{
    public interface IFloorMapService
    {
        Task<List<FloorMap>> GetAllFloorMaps();
        Task<FloorMap> GetFloorMapById(int id);
        Task<bool> AddFloorMap(FloorMap floorMap);
        Task<bool> UpdateFloorMap(FloorMap floorMap, int id);
        Task<bool> DeleteFloorMap(int id);
    }
}
