﻿using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccessLayer.Interfaces
{
    public interface IFloorMapRepository : IRepository<FloorMap>
    {
        Task<int> UpdateFloorMap(FloorMap floorMap, int id);
    }
}
