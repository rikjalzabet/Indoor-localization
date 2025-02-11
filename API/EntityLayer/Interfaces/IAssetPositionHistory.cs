﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace EntityLayer.Interfaces
{
    public interface IAssetPositionHistory
    {
        public int Id { get; set; }
        public int AssetId { get; set; }
        public double X { get; set; }
        public double Y { get; set; }
        public DateTime DateTime { get; set; }
        public int FloorMapId { get; set; }
    }
}
