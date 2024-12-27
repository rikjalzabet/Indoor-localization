﻿using EntityLayer.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BusinessLogicLayer.Interfaces
{
    public interface IAssetZoneHistoryService
    {
        Task UpdateZoneHistory(AssetPositionHistory position);
    }
}
